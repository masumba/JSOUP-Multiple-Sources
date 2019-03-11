package com.nynelyne.luse;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mutale Chanda on 09/03/2019.
 */
public class NewsHeadlinesAfricanFragment extends Fragment {

    View view;

    private ProgressDialog mProgressDialog;
    private String url = "https://www.cnbc.com/africa/";
    private ArrayList<String> mNewsHeadlineList = new ArrayList<>();

    public NewsHeadlinesAfricanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_african, container, false);

        new Description().execute();

        return view;
    }

    private class Description extends AsyncTask<Void, Void, Void>
    {
        String desc;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewsHeadlinesAfricanFragment.super.getContext());
            mProgressDialog.setTitle("Business News Headlines");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            try {
                mNewsHeadlineList.clear();
                //Connect to the website
                Document mNewsHeadlineDocument = Jsoup.connect(url).get();
                //Using Elements to get the Meta data
                Elements mElementDataSize = mNewsHeadlineDocument.select("div[class=headline]");
                //locate the content attribute
                int mElementSize = mElementDataSize.size();

                for (int i = 0; i < mElementSize; i++)
                {
                    Elements mElementNewsHeadline = mNewsHeadlineDocument.select("div[class=headline]").eq(i);
                    String mNewsHeadline = mElementNewsHeadline.text();

                    mNewsHeadlineList.add(mNewsHeadline);
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void results)
        {
            // Set description into TextView
            RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.african_recyclerView);

            NewsHeadlinesAfricanDataAdapter mNewsHeadlinesAfricanDataAdapter = new NewsHeadlinesAfricanDataAdapter(NewsHeadlinesAfricanFragment.super.getContext(), mNewsHeadlineList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mNewsHeadlinesAfricanDataAdapter);

            mProgressDialog.dismiss();
        }
    }


}
