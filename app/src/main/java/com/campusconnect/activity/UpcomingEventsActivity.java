package com.campusconnect.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campusconnect.R;
import com.campusconnect.adapter.UpcomingEventsAdapterActivity;
import com.campusconnect.bean.CampusFeedBean;
import com.campusconnect.communicator.WebRequestTask;
import com.campusconnect.communicator.WebServiceDetails;
import com.campusconnect.constant.AppConstants;
import com.campusconnect.supportClasses.UpcomingEvents_infoActivity;
import com.campusconnect.utility.SharedpreferenceUtility;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RK on 05/11/2015.
 */
public class UpcomingEventsActivity extends ActionBarActivity {

    RecyclerView upcoming_events;
    LinearLayout close;
    Typeface r_med;
    TextView upcoming_events_text;
    ArrayList<CampusFeedBean> eventList= new ArrayList<CampusFeedBean>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);
        String clubId= getIntent().getStringExtra("clubId");
        WebApiGetUpComingEvent(clubId);



        r_med = Typeface.createFromAsset(getAssets(), "font/Roboto_Medium.ttf");

        close = (LinearLayout) findViewById(R.id.cross_button);
        upcoming_events_text = (TextView) findViewById(R.id.tv_upcoming_events);
        upcoming_events_text.setTypeface(r_med);

        upcoming_events = (RecyclerView) findViewById(R.id.rv_upcoming_events);
        upcoming_events.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        upcoming_events.setLayoutManager(llm);
        upcoming_events.setItemAnimator(new DefaultItemAnimator());


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void WebApiGetUpComingEvent(String clubId) {
        try {
            String pid = SharedpreferenceUtility.getInstance(UpcomingEventsActivity.this).getString(AppConstants.PERSON_PID);
            JSONObject jsonObject = new JSONObject();
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            String url = WebServiceDetails.DEFAULT_BASE_URL + "getEvents?clubId="+clubId;
            new WebRequestTask(UpcomingEventsActivity.this, param, _handler, WebRequestTask.GET, jsonObject, WebServiceDetails.PID_GET_Events,
                    true, url).execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private List<UpcomingEvents_infoActivity> createList_upcomingEventsAdapterActivity(int size) {
        List<UpcomingEvents_infoActivity> result = new ArrayList<UpcomingEvents_infoActivity>();
        for (int i = 1; i <= size; i++) {
            UpcomingEvents_infoActivity ci = new UpcomingEvents_infoActivity();
            result.add(ci);
        }

        return result;
    }

    private final Handler _handler = new Handler() {
        public void handleMessage(Message msg) {
            int response_code = msg.what;
            if (response_code != 0 && response_code != 204) {
                String strResponse = (String) msg.obj;
                Log.v("Response", strResponse);
                if (strResponse != null && strResponse.length() > 0) {
                    switch (response_code) {
                        case WebServiceDetails.PID_GET_Events: {
                            try {

                                JSONObject jsonObject = new JSONObject(strResponse);
                                if (jsonObject.has("items")) {

                                    JSONArray array = jsonObject.getJSONArray("items");
                                    if (array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {

                                          //  "event_creator": "Key('Profile', 5383895587487744)",
                                                  //  "eventId": "5283876536582144",
                                                 //   "attendees": "[Key('Profile', 5383895587487744), Key('Profile', 5638059940904960), Key('Profile', 4834276138811392)]",
                                                  //  "description": "test",
                                                  //  "end_date": "2015-12-11",
                                                  //  "title": "Attend this test",
                                                 //   "start_time": "06:00:00",
                                                  //  "club_id": "Key('Club', 6477919320801280)",
                                                //    "venue": "SAC",
                                              //      "start_date": "2015-12-11",
                                                //    "collegeId": "Key('CollegeDb', 6242363718500352)",
                                                  //  "completed": "No",
                                                 //   "end_time": "15:00:00",
                                                //    "time": "06:00:00",
                                                //    "date": "2015-12-11",
                                                //    "isAlumni": "No",
                                             //       "club_name": "ClubTwo",
                                            //        "views": "0",
                                          //          "tags": "[]",
                                       //             "kind": "clubs#resourcesItem"
                                            JSONObject innerObj = array.getJSONObject(i);
                                            CampusFeedBean bean = new CampusFeedBean();

                                            String eventCreator = innerObj.optString("event_creator");
                                            String description = innerObj.optString("description");
                                            String views = innerObj.optString("views");
                                            String photo = innerObj.optString("photo");

                                            //TODO remvpe comment here

                                           // String clubid = innerObj.optString("club_id");
                                            String eventid = innerObj.optString("eventId");
                                            String timeStamp = innerObj.optString("timestamp");
                                            String title = innerObj.optString("title");

                                            String collegeId = innerObj.optString("collegeId");
                                            String kind = innerObj.optString("kind");
                                            String endDate = innerObj.optString("end_date");
                                            String startDate = innerObj.optString("start_date");

                                            String startTime = innerObj.optString("start_time");
                                            String endTime = innerObj.optString("end_time");
                                            String venue = innerObj.optString("venue");
                                            String clubphoto = innerObj.optString("photoUrl");


                                            String date= innerObj.optString("time");
                                            String time=innerObj.optString("date");
                                            String liker = innerObj.optString("views");
                                            String complete = innerObj.optString("completed");

                                            String alumni=innerObj.optString("isAlumni");
                                            String clubName=innerObj.optString("club_name");
                                            ArrayList<String> attendList = new ArrayList<>();
                                           try {

                                               if (innerObj.has("attendees")) {
                                                   JSONArray attArray = innerObj.getJSONArray("attendees");
                                                   if (attArray.length() > 0) {
                                                       for (int j = 0; j < attArray.length(); j++) {
                                                           String attendStr = attArray.getString(j);
                                                           attendList.add(attendStr);
                                                       }
                                                   }
                                               }
                                           }catch (Exception ex){
                                               ex.printStackTrace();
                                           }

                                            bean.setEventCreator(eventCreator);
                                            bean.setDescription(description);
                                            bean.setViews(views);
                                        //    bean.setClubid(clubid);
                                            bean.setPid(eventid);

                                            bean.setPhoto(photo);
                                            bean.setTimeStamp(timeStamp);
                                            bean.setTitle(title);
                                            bean.setCollegeId(collegeId);

                                            bean.setKind(kind);
                                            bean.setAttendees(attendList);
                                            bean.setEnd_date("" + endDate);
                                            bean.setStart_date("" + startDate);

                                            bean.setStart_time("" + startTime);
                                            bean.setEnd_time("" + endTime);
                                            bean.setVenue("" + venue);
                                            bean.setClubphoto("" + clubphoto);

                                            bean.setLikers("" + liker);
                                            bean.setCompleted("" + complete);
                                            bean.setClubname(clubName);
                                            ArrayList<String> tagList = new ArrayList<>();
                                            try {

                                                if (innerObj.has("tags")) {

                                                    JSONArray tagArray = innerObj.getJSONArray("tags");
                                                    if (tagArray.length() > 0) {
                                                        for (int l = 0; l < tagArray.length(); l++) {
                                                            String tag = tagArray.getString(l);
                                                            tagList.add(tag);
                                                        }

                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            bean.setTag(tagList);
                                            bean.setTime(time);
                                            bean.setDate(date);
                                            bean.setAlumni(alumni);

                                            eventList.add(bean);
                                        }
                                        UpcomingEventsAdapterActivity upcomingEventsAdapterActivity = new UpcomingEventsAdapterActivity(
                                                eventList,UpcomingEventsActivity.this);
                                        upcoming_events.setAdapter(upcomingEventsAdapterActivity);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;

                        default:
                            break;
                    }
                } else {
                    Toast.makeText(UpcomingEventsActivity.this, "SERVER_ERROR", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(UpcomingEventsActivity.this, "SERVER_ERROR", Toast.LENGTH_LONG).show();
            }
        }
    };
}

