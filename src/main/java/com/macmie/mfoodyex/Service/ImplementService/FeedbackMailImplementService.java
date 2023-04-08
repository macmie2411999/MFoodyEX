package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.FeedbackMail;
import com.macmie.mfoodyex.Repository.FeedbackMailRepository;
import com.macmie.mfoodyex.Service.InterfaceService.FeedbackMailInterfaceService;
import com.macmie.mfoodyex.Util.StringUtil;
import com.macmie.mfoodyex.Util.TextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j

/* @Transactional: Handle rollback when exceptions occur
 * @Slf4j: Spring Boot Logging
 * */
public class FeedbackMailImplementService implements FeedbackMailInterfaceService {
    @Autowired
    private FeedbackMailRepository feedbackMailRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<FeedbackMail> getListFeedbackMails() {
        log.info("Fetching all FeedbackMails ");
        return feedbackMailRepository.findAll();
    }

    @Override
    public FeedbackMail getFeedbackMailByID(int idFeedbackMail) {
        log.info("Fetching FeedbackMail with ID: {}", idFeedbackMail);
        return feedbackMailRepository.findById(idFeedbackMail).orElse(null);
    }

    @Override
    public FeedbackMail getFeedbackMailByEmailUserAndContentFeedbackMail(String emailUser, String contentFeedbackMail) {
        log.info("Fetching FeedbackMail with emailUser: {} and contentFeedbackMail: {}", emailUser, contentFeedbackMail );
        return feedbackMailRepository.findByEmailUserFeedbackMailAndContentFeedbackMail(emailUser, contentFeedbackMail);
    }

    @Override
    public FeedbackMail saveFeedbackMail(FeedbackMail feedbackMail) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        feedbackMail.setEmailUserFeedbackMail(stringUtil.parseEmail(feedbackMail.getEmailUserFeedbackMail()));
//        feedbackMail.setNameUserFeedbackMail(stringUtil.parseName(feedbackMail.getNameUserFeedbackMail()));
//        feedbackMail.setTitleFeedbackMail(textUtil.parseToLegalText(feedbackMail.getTitleFeedbackMail()));
//        feedbackMail.setContentFeedbackMail(textUtil.parseToLegalText(feedbackMail.getContentFeedbackMail()));
        log.info("Saving FeedbackMail with ID: {}", feedbackMail.getIdFeedbackMail());
        return feedbackMailRepository.save(feedbackMail);
    }

    @Override
    public FeedbackMail updateFeedbackMail(FeedbackMail newFeedbackMail) {
//        FeedbackMail feedbackMailToUpdate = feedbackMailRepository.getById(newFeedbackMail.getIdFeedbackMail());
//        feedbackMailToUpdate.setIdFeedbackMail((newFeedbackMail.getIdFeedbackMail()));
//        feedbackMailToUpdate.setEmailUserFeedbackMail(stringUtil.parseEmail(newFeedbackMail.getEmailUserFeedbackMail()));
//        feedbackMailToUpdate.setNameUserFeedbackMail(stringUtil.parseName(newFeedbackMail.getNameUserFeedbackMail()));
//        feedbackMailToUpdate.setTitleFeedbackMail(textUtil.parseToLegalText(newFeedbackMail.getTitleFeedbackMail()));
//        feedbackMailToUpdate.setContentFeedbackMail(textUtil.parseToLegalText(newFeedbackMail.getContentFeedbackMail()));
        log.info("Updating FeedbackMail with ID: {}", newFeedbackMail.getIdFeedbackMail());
        return feedbackMailRepository.save(newFeedbackMail);
    }

    @Override
    public void deleteFeedbackMailByID(int idFeedbackMail) {
        log.info("Deleting FeedbackMail with ID: {}", idFeedbackMail);
        feedbackMailRepository.deleteById(idFeedbackMail);
    }

    @Override
    public Long countTotalNumberOfFeedbackMails() {
        log.info("Count Total Number of FeedbackMails");
        return feedbackMailRepository.countTotalNumberOfFeedbackMails();
    }
}
