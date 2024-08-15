package kr.mini_project.Dao;

import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyPageDao {
    @Autowired
    SqlSession sqlSession;


    public List<MypageDto> getPlayList(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.getPlayList", mypageDto);
    }

    public void insertLike(MypageDto mypageDto) {
        sqlSession.insert("myPageMapper.insertLike", mypageDto);
    }

    public void deleteLike(MypageDto mypageDto) {
        sqlSession.delete("myPageMapper.deleteLike", mypageDto);
    }

    public List<MypageDto> getLikesList(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.getLikesList", mypageDto);
    }

    public List<TicketDto> getTicketList(String userId) {
        return sqlSession.selectList("myPageMapper.getTicketList", userId);
    }

    public void insertBuyUser(TicketDto ticketDto) {
        sqlSession.insert("myPageMapper.insertBuyUser", ticketDto);
    }

    public void updateUserTickNo(TicketDto ticketDto) {
        sqlSession.update("myPageMapper.updateUserTickNo", ticketDto);
    }

    public List<TicketDto> getTicketBuyDtl(String ticketNo) {
       return sqlSession.selectList("myPageMapper.getTicketBuyDtl", ticketNo);
    }

    public String getMyTicket(TicketDto ticketDto) {
        return sqlSession.selectOne("myPageMapper.getMyTicket", ticketDto);
    }

    public List<MypageDto> getPlayListDtl(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.getPlayListDtl", mypageDto);
    }

    public int getMyPlayListCnt(MypageDto mypageDto) {
        return sqlSession.selectOne("myPageMapper.getMyPlayListCnt", mypageDto);
    }

    public void deletePlayList(MypageDto mypageDto) {
        sqlSession.delete("myPageMapper.deletePlayList", mypageDto);
    }

    public int getMaxPlayNo() {
        return sqlSession.selectOne("myPageMapper.getMaxPlayNo");
    }

    public void insertPlayList(MypageDto mypageDto) {
        sqlSession.insert("myPageMapper.insertPlayList", mypageDto);
    }

    public void insertComment(MypageDto mypageDto) {
        sqlSession.insert("myPageMapper.insertComment", mypageDto);
    }

    public List<MypageDto> selectComment(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.selectComment", mypageDto);
    }

    public List<MypageDto> getMyCommList(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.getMyCommList", mypageDto);
    }

    public void insertCommReport(MypageDto mypageDto) {
        sqlSession.update("myPageMapper.insertCommReport", mypageDto);
    }

    public int selectMyRepoCnt(MypageDto mypageDto) {
        return sqlSession.selectOne("myPageMapper.selectMyRepoCnt", mypageDto);
    }

    public int myCommentChk(MypageDto mypageDto) {
        return sqlSession.selectOne("myPageMapper.myCommentChk", mypageDto);
    }

    public void updateCommentDel(MypageDto mypageDto) {
        sqlSession.update("myPageMapper.updateCommentDel", mypageDto);
    }

    public List<MypageDto> getMyBuyTicketList(MypageDto mypageDto) {
        return sqlSession.selectList("myPageMapper.getMyBuyTicketList", mypageDto);
    }

    public TicketDto getMyticketDttm(TicketDto ticketDto) {
        return sqlSession.selectOne("myPageMapper.getMyticketDttm", ticketDto);
    }

    public void updateTicketEndDttm(TicketDto ticketDto) {
        sqlSession.update("myPageMapper.updateTicketEndDttm", ticketDto);
    }
}
