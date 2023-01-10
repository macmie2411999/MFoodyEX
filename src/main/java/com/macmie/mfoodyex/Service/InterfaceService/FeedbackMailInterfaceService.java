package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FeedbackMail;

import java.util.List;

public interface FeedbackMailInterfaceService {
    public List<FeedbackMail> getListFeedbackMail();

    public FeedbackMail getFeedbackMail(int ID_FeedbackMail);

    public FeedbackMail saveFeedbackMail(FeedbackMail feedbackMail);

    public void deleteFeedbackMail(int ID_FeedbackMail);
}
