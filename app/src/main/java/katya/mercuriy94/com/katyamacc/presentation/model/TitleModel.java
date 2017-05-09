package katya.mercuriy94.com.katyamacc.presentation.model;

import android.support.annotation.StringRes;

/**
 * Created by nikita on 04.02.2017.
 */

public class TitleModel {

    private String mImageWeb;
    private boolean mVisibleBackButton;
    @StringRes
    private int mTitleMessageRes;
    private String mTitleMessage;

    public String getImageWeb() {
        return mImageWeb;
    }

    public void setImageWeb(String imageWeb) {
        mImageWeb = imageWeb;
    }

    public boolean isVisibleBackButton() {
        return mVisibleBackButton;
    }

    public void setVisibleBackButton(boolean visibleBackButton) {
        mVisibleBackButton = visibleBackButton;
    }

    @StringRes
    public int getTitleMessageRes() {
        return mTitleMessageRes;
    }

    public void setTitleMessageRes(int titleMessageRes) {
        mTitleMessageRes = titleMessageRes;
    }


    public String getTitleMessage() {
        return mTitleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        mTitleMessage = titleMessage;
    }

    public static class Builder {

        private String mImageWeb;
        private boolean mVisibleBackButton;
        @StringRes
        private int mTitleMessageRes;
        private String mTitleMessage;

        public String getImageWeb() {
            return mImageWeb;
        }

        public Builder setImageWeb(String imageWeb) {
            mImageWeb = imageWeb;
            return this;
        }

        public boolean isVisibleBackButton() {
            return mVisibleBackButton;
        }

        public Builder setVisibleBackButton(boolean visibleBackButton) {
            mVisibleBackButton = visibleBackButton;
            return this;
        }

        @StringRes
        public int getTitleMessageRes() {
            return mTitleMessageRes;
        }

        public Builder setTitleMessageRes(@StringRes int stringResTitleMessage) {
            mTitleMessageRes = stringResTitleMessage;
            return this;
        }


        public String getTitleMessage() {
            return mTitleMessage;
        }

        public Builder setTitleMessage(String titleMessage) {
            mTitleMessage = titleMessage;
            return this;
        }

        public TitleModel build() {
            TitleModel title = new TitleModel();
            title.setTitleMessageRes(mTitleMessageRes);
            title.setImageWeb(mImageWeb);
            title.setVisibleBackButton(mVisibleBackButton);
            title.setTitleMessage(mTitleMessage);
            return title;
        }
    }

}
