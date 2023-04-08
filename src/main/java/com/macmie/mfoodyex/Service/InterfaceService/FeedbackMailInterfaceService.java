package com.macmie.mfoodyex.Service.InterfaceService;

import com.macmie.mfoodyex.Model.FeedbackMail;

import java.util.List;

public interface FeedbackMailInterfaceService {
    public List<FeedbackMail> getListFeedbackMails();

    public FeedbackMail getFeedbackMailByID(int idFeedbackMail);

    public FeedbackMail getFeedbackMailByEmailUserAndContentFeedbackMail(String emailUser, String contentFeedbackMail);

    public FeedbackMail saveFeedbackMail(FeedbackMail feedbackMail);

    public FeedbackMail updateFeedbackMail(FeedbackMail newFeedbackMail);

    public void deleteFeedbackMailByID(int idFeedbackMail);

    public Long countTotalNumberOfFeedbackMails();
}
