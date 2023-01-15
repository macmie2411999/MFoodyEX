package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.POJO.FeedbackMailPOJO;

import java.util.List;

public interface FeedbackMailInterfaceService {
    public List<FeedbackMail> getListFeedbackMails();

    public FeedbackMail getFeedbackMailByID(int ID_FeedbackMail);

    public FeedbackMail saveFeedbackMail(FeedbackMail feedbackMail);

    public FeedbackMail updateFeedbackMail(FeedbackMail newFeedbackMail);

    public void deleteFeedbackMailByID(int ID_FeedbackMail);
}
