package com.macmie.mfoodyex.Service.ImplementService;

import com.macmie.mfoodyex.Model.CommentMfoody;
import com.macmie.mfoodyex.Repository.CommentMfoodyRepository;
import com.macmie.mfoodyex.Service.InterfaceService.CommentMfoodyInterfaceService;
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
public class CommentMfoodyImplementService implements CommentMfoodyInterfaceService {
    @Autowired
    private CommentMfoodyRepository commentMfoodyRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<CommentMfoody> getListCommentMfoodys() {
        log.info("Fetching all CommentMfoodys: ");
        return commentMfoodyRepository.findAll();
    }

    @Override
    public CommentMfoody getCommentMfoodyByID(int ID_CommentMfoody) {
        log.info("Fetching CommentMfoody with ID: {}", ID_CommentMfoody);
        return commentMfoodyRepository.findById(ID_CommentMfoody).orElse(null);
    }

    @Override
    public CommentMfoody saveCommentMfoody(CommentMfoody commentMfoody) {
//        feedbackMail.setIdFeedbackMail(feedbackMail.getIdFeedbackMail());
//        CommentMfoody.setNameUserCard(stringUtil.parseName(CommentMfoody.getNameUserCard()));

        log.info("Saving CommentMfoody with ID: {}", commentMfoody.getIdComment());
        return commentMfoodyRepository.save(commentMfoody);
    }

    @Override
    public CommentMfoody updateCommentMfoody(CommentMfoody newCommentMfoody) {
        CommentMfoody commentMfoodyToUpdate = commentMfoodyRepository.getById(newCommentMfoody.getIdComment());
        commentMfoodyToUpdate.setRatingComment((newCommentMfoody.getRatingComment()));
        commentMfoodyToUpdate.setContentComment((newCommentMfoody.getContentComment()));
        commentMfoodyToUpdate.setIdUser((newCommentMfoody.getIdUser()));
        commentMfoodyToUpdate.setIdProduct((newCommentMfoody.getIdProduct()));

        log.info("Updating UserMfoody with ID: {}", newCommentMfoody.getIdComment());
        return commentMfoodyRepository.save(commentMfoodyToUpdate);
    }

    @Override
    public void deleteCommentMfoodyByID(int ID_CommentMfoody) {
        log.info("Deleting CommentMfoody with ID: {}", ID_CommentMfoody);
        commentMfoodyRepository.deleteById(ID_CommentMfoody);
    }
}
