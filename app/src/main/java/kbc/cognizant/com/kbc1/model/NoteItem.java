package kbc.cognizant.com.kbc1.model;

/**
 * Created by cts_mobility5 on 3/15/16.
 */
public class NoteItem {
    private int mId;
    private String mTitle;
    private String mNote;
    private String mLastModifiedDate;


    public NoteItem(int id ,String title, String note, String lstModifiedDate){
        this.mId = id;
        this.mTitle = title;
        this.mNote = note;
        this.mLastModifiedDate =lstModifiedDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getNote() {
        return mNote;
    }

    public String getLastModifiedDate() {
        return mLastModifiedDate;
    }

    public int getId() {
        return mId;
    }
}
