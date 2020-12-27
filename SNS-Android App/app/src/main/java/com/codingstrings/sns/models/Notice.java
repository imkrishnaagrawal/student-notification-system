package com.codingstrings.sns.models;

public class Notice {
    public final String  _id ;
    public final String  message ;
    public final String  msubject ;
    public final String  seen;


    public Notice(String _id, String msubject, String message , String seen) {
        this._id = _id;
        this.message = message;
        this.msubject = msubject;
        this.seen = seen;
    }
}