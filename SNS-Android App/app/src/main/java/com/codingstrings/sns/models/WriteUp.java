package com.codingstrings.sns.models;

public class WriteUp {
    public final String  _id ;
    public final String  name ;
    public final String  subject ;
    public final String  number;
    public final String  seen;
    public final String  group;

    public WriteUp(String _id, String name, String subject, String number, String seen, String group) {
        this._id = _id;
        this.name = name;
        this.subject = subject;
        this.number = number;
        this.seen = seen;
        this.group = group;

    }
}