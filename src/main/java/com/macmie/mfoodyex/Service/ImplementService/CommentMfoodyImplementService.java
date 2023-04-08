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
        log.info("Fetching all CommentMfoodys ");
        return commentMfoodyRepository.findAll();
    }

    @Override
    public List<CommentMfoody> getListCommentMfoodysByIdProduct(int idProduct) {
        log.info("Fetching all CommentMfoodys by idProduct: {}", idProduct);
        return commentMfoodyRepository.findAllByIdProduct(idProduct);
    }

    @Override
    public List<CommentMfoody> getListCommentMfoodysByIdUser(int idUser) {
        log.info("Fetching all CommentMfoodys by idUser: {}", idUser);
        return commentMfoodyRepository.findAllByIdUser(idUser);
    }

    @Override
    public CommentMfoody getCommentMfoodyByID(int idCommentMfoody) {
        log.info("Fetching CommentMfoody with ID: {}", idCommentMfoody);
        return commentMfoodyRepository.findById(idCommentMfoody).orElse(null);
    }

    @Override
    public CommentMfoody getCommentMfoodyByContentComment(String contentComment) {
        log.info("Fetching CommentMfoody with contentComment: {}", contentComment);
        return commentMfoodyRepository.findCommentMfoodyByContentComment(contentComment);
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
//        CommentMfoody commentMfoodyToUpdate = commentMfoodyRepository.getById(newCommentMfoody.getIdComment());
//        commentMfoodyToUpdate.setRatingComment((newCommentMfoody.getRatingComment()));
//        commentMfoodyToUpdate.setContentComment((newCommentMfoody.getContentComment()));
//        commentMfoodyToUpdate.setUser((newCommentMfoody.getUser()));
//        commentMfoodyToUpdate.setProduct((newCommentMfoody.getProduct()));

        log.info("Updating CommentMfoody with ID: {}", newCommentMfoody.getIdComment());
        return commentMfoodyRepository.save(newCommentMfoody);
    }

    @Override
    public void deleteCommentMfoodyByID(int idCommentMfoody) {
        log.info("Deleting CommentMfoody with ID: {}", idCommentMfoody);
        commentMfoodyRepository.deleteById(idCommentMfoody);
    }

    @Override
    public void deleteAllCommentsMfoodyByIdUser(int idUser) {
        log.info("Deleting All CommentMfoodys with idUser: {}", idUser);
        commentMfoodyRepository.deleteAllByIdUser(idUser);
    }

    @Override
    public void deleteAllCommentsMfoodyByIdProduct(int idProduct) {
        log.info("Deleting All CommentMfoodys with idProduct: {}", idProduct);
        commentMfoodyRepository.deleteAllByIdProduct(idProduct);
    }

    @Override
    public Long countTotalNumberOfCommentMfoodys() {
        log.info("Count Total Number of CommentMfoodys");
        return commentMfoodyRepository.countTotalNumberOfCommentMfoodys();
    }
}
