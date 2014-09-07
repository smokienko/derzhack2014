package com.smokiyenko.burokrat.app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentChecklistFragment extends Fragment {

    private static final String DOCUMENT_NAME = "documentName";

    private String documentName;
    private String mParam2;
    private String[] steps = {"Скачайте анкеты", "Заполните анкеты", "Отнисете анкеты по месту регистрации", "Профит"};
    private ListView stepsListView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param documentName Parameter 1.
     * @return A new instance of fragment DocumentChecklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentChecklistFragment newInstance(final String documentName) {
        DocumentChecklistFragment fragment = new DocumentChecklistFragment();
        Bundle args = new Bundle();
        args.putString(DOCUMENT_NAME, documentName);
        fragment.setArguments(args);
        return fragment;
    }
    public DocumentChecklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            documentName = getArguments().getString(DOCUMENT_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_document_checklist, container, false);
        stepsListView = (ListView) view.findViewById(R.id.steps_list);
        stepsListView.setAdapter(new DocumentsCheckBoxAdapter(getActivity(), Arrays.asList(this.steps)));
        return view;
    }

}
