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
    public List<FeedbackMail> getListFeedbackMail() {
        log.info("Fetching all FeedbackMail");
        return feedbackMailRepository.findAll();
    }

    @Override
    public FeedbackMail getFeedbackMail(int ID_FeedbackMail) {
        log.info("Fetching FeedbackMail with id: {}", ID_FeedbackMail);
        return feedbackMailRepository.findById(ID_FeedbackMail).orElse(null);
    }

    @Override
    public FeedbackMail saveFeedbackMail(FeedbackMail feedbackMail) {
        feedbackMail.setEmailUserFeedbackMail(stringUtil.parseEmail(feedbackMail.getEmailUserFeedbackMail()));
        feedbackMail.setNameUserFeedbackMail(stringUtil.parseName(feedbackMail.getNameUserFeedbackMail()));
        feedbackMail.setTitleFeedbackMail(textUtil.parseToLegalText(feedbackMail.getTitleFeedbackMail()));
        feedbackMail.setContentFeedbackMail(textUtil.parseToLegalText(feedbackMail.getContentFeedbackMail()));
        log.info("Saving FeedbackMail with email: {}", feedbackMail.getEmailUserFeedbackMail());
        return feedbackMailRepository.save(feedbackMail);
    }

    @Override
    public void deleteFeedbackMail(int ID_FeedbackMail) {
        log.info("Deleting thu_phan_hoi with id: {}", ID_FeedbackMail);
        feedbackMailRepository.deleteById(ID_FeedbackMail);
    }
}
