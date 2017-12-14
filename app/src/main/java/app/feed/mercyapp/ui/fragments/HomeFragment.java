package app.feed.mercyapp.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;
import java.util.List;

import app.feed.mercyapp.ChildAnimationExample;
import app.feed.mercyapp.R;
import app.feed.mercyapp.SliderLayout;
import app.feed.mercyapp.adapters.HomeAdapter;
import app.feed.mercyapp.connection.ApiClient;
import app.feed.mercyapp.connection.Apinterface;
import app.feed.mercyapp.connection.ErrorUtils;
import app.feed.mercyapp.models.responses.FeedResponse;
import app.feed.mercyapp.utills.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private ProgressDialog pDialog;


    private static String url = "http://10.2.3.1:81/CancerAlliance/ViewData";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        setUpSlider();

        swipe.setRefreshing(true);
        setUpv();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        return view;
    }



   /* private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject c = jsonObj.getJSONObject(i);
                        String Feed = "Feed: " + c.getString(Config.KEY_Feed) ;
                        String Date = "Date: " + c.getString(Config.KEY_Date);
                        String Tittle = "Tittle: " + c.getString(Config.KEY_Tittle) ;
                        String Image = "Image: " + c.getString(Config.KEY_Image) ;

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put(Config.KEY_Tittle, Tittle);
                        contact.put(Config.KEY_Feed, Feed);
                        contact.put(Config.KEY_Date, Date);
                        contact.put(Config.KEY_Image, Image);



                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Data is not available at the moment. Try Again later!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            ListAdapter adapter = new SimpleAdapter(
                    FinalDetails.this, contactList,
                    R.layout.list_item, new String[]{"Name", "Phone",
                    "Pressure1"}, new int[]{R.id.name,
                    R.id.email, R.id.press});

            lv.setAdapter(adapter);
        }

    }*/

    private void runOnUiThread(Runnable runnable) {
    }


    private void setUpv() {

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setStackFromEnd(true);
        lm.setReverseLayout(true);

        rv.setLayoutManager(lm);

        getData();

    }


    private void getData(){

        Apinterface apinterface = ApiClient.getClient().create(Apinterface.class);
        Call<List<FeedResponse>> call =  apinterface.getFeed();
        call.enqueue(new Callback<List<FeedResponse>>() {
            @Override
            public void onResponse(Call<List<FeedResponse>> call, Response<List<FeedResponse>> response) {

                swipe.setRefreshing(false);

                if(response.code() == 200){


                    HomeAdapter homeAdapter = new HomeAdapter(getActivity(), response.body());
                    rv.setAdapter(homeAdapter);

                }else {

                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<FeedResponse>> call, Throwable t) {

                swipe.setRefreshing(false);
                Toast.makeText(getActivity(), new ErrorUtils().parseOnFailure(t), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void setUpSlider() {

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("1", R.drawable.wall_one);
        file_maps.put("2", R.drawable.wallpaper_two);
        file_maps.put("3", R.drawable.wallpaper_three);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });


            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            slider.addSlider(textSliderView);
        }


        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new ChildAnimationExample());
        slider.setDuration(3000);
        // slider.addOnPageChangeListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
