package kr.mini_project.Svc;

import kr.mini_project.Dao.AdminDao;
import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import kr.mini_project.Dto.joinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminSvcImpl implements AdminSvc{

    @Autowired
    AdminDao adminDao;

    @Override
    public List<joinDto> getUserList(Map<String, Object> searchMap) {
        return adminDao.getUserList(searchMap);
    }

    @Override
    public List<TicketDto> getAllTicketList(Map<String, Object> searchMap) {
        return adminDao.getAllTicketList(searchMap);
    }

    @Override
    public void insertTicket(TicketDto ticketDto) {
        adminDao.insertTicket(ticketDto);
    }

    @Override
    public void updateUserStats(String userId) {
        adminDao.updateUserStats(userId);
    }

    @Override
    public List<MypageDto> getAllCommentList(Map<String, Object> searchMap) {
        return adminDao.getAllCommentList(searchMap);
    }

    @Override
    public List<MypageDto> selectReportList(Map<String, Object> searchMap) {
        return adminDao.selectReportList(searchMap);
    }

    @Override
    public void deleteVideo(String videoNo) {
        adminDao.deleteVideo(videoNo);
    }

    @Override
    public void insertVideo(MuflixDto muflixDto) {
        adminDao.insertVideo(muflixDto);
    }

    @Override
    public MuflixDto getVideoDtl(String videoNo) {
        return adminDao.getVideoDtl(videoNo);
    }

    @Override
    public void updateVideo(MuflixDto muflixDto) {
        adminDao.updateVideo(muflixDto);
    }
}
