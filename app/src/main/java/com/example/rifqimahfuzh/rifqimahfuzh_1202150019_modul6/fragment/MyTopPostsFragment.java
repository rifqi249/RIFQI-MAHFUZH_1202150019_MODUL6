package com.example.rifqimahfuzh.rifqimahfuzh_1202150019_modul6.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Umam on 3/31/2018.
 */

public class MyTopPostsFragment extends PostListFragment {
    public MyTopPostsFragment() {}



    @Override

    public Query getQuery(DatabaseReference databaseReference) {

        // [START my_top_posts_query]

        // My top posts by number of stars

        String myUserId = getUid();

        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)

                .orderByChild("starCount");

        // [END my_top_posts_query]



        return myTopPostsQuery;

    }
}
