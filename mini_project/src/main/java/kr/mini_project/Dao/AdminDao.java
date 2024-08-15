package kr.mini_project.Dao;

import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import kr.mini_project.Dto.joinDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminDao {

    @Autowired
    SqlSession sqlSession;

    public List<joinDto> getUserList(Map<String, Object> searchMap) {
        return sqlSession.selectList("adminMapper.getUserList", searchMap);
    }

    public List<TicketDto> getAllTicketList(Map<String, Object> searchMap) {
        return sqlSession.selectList("adminMapper.getAllTicketList", searchMap);
    }

    public void insertTicket(TicketDto ticketDto) {
        sqlSession.insert("adminMapper.insertTicket", ticketDto);
    }

    public void updateUserStats(String userId) {
        sqlSession.update("adminMapper.updateUserStats", userId);
    }

    public List<MypageDto> getAllCommentList(Map<String, Object> searchMap) {
        return sqlSession.selectList("adminMapper.getAllCommentList", searchMap);
    }

    public List<MypageDto> selectReportList(Map<String, Object> searchMap) {
        return sqlSession.selectList("adminMapper.selectReportList", searchMap);
    }

    public void deleteVideo(String videoNo) {
        sqlSession.delete("adminMapper.deleteVideo", videoNo);
    }

    public void insertVideo(MuflixDto muflixDto) {
        sqlSession.insert("adminMapper.insertVideo", muflixDto);
    }

    public MuflixDto getVideoDtl(String videoNo) {
        return sqlSession.selectOne("adminMapper.getVideoDtl", videoNo);
    }

    public void updateVideo(MuflixDto muflixDto) {
        sqlSession.update("adminMapper.updateVideo", muflixDto);
    }
}
