package com.smokiyenko.burokrat.app;

/**
 * Created by s.mokiyenko on 9/7/14.
 */
public class DocumentsListResponse {

    private final String[] documentsList;


    public DocumentsListResponse(String[] documentsList) {
        this.documentsList = documentsList;
    }

    public String[] getDocumentsList() {
        return documentsList;
    }

}
