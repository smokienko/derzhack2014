package com.smokiyenko.burokrat.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends Activity implements DocumentFragment.OnFragmentInteractionListener {

    private final String[] documentNames = {"Документ 1",
                                            "Документ 2",
                                            "Документ 3",
                                            "Документ 4",
                                            "Документ 5",
                                            "Документ 6",
                                            "Документ 7",
                                            "Документ 8",
                                            "Документ 9",
                                            "Документ 10"};
    private SessionResponse session;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        session = BurokratApplication.getEventBus().getStickyEvent(SessionResponse.class);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null){
            DocumentFragment fragment = DocumentFragment.newInstance();
            final ArrayList<String> documents = new ArrayList<String>();
            documents.addAll(Arrays.asList(documentNames));
            fragment.setDocuments(documents);
            getFragmentManager().beginTransaction().add(R.id.fragment_container,fragment,"doc").commit();
        }
    }

    @SuppressWarnings("Unused")
    public void onEventMainThread(final DocumentsListResponse documentsListResponse){
       getFragmentManager().getFragment(null,"doc");
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigateUp() {
        if (getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
            getActionBar().setTitle(R.string.title_activity_search);
        } else {
            onBackPressed();
        }
        return true;
    }




    @Override
    public void onFragmentInteraction(final String name) {
        getActionBar().setTitle(name);
        DocumentChecklistFragment fragment = DocumentChecklistFragment.newInstance(name);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack("doc").commit();
    }
}
