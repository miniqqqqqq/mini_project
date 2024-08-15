package kr.mini_project.Svc;

import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import kr.mini_project.Dto.joinDto;

import java.util.List;
import java.util.Map;

public interface AdminSvc {
    List<joinDto> getUserList(Map<String, Object> searchMap);

    List<TicketDto> getAllTicketList(Map<String, Object> searchMap);

    void insertTicket(TicketDto ticketDto);

    void updateUserStats(String userId);

    List<MypageDto> getAllCommentList(Map<String, Object> searchMap);

    List<MypageDto> selectReportList(Map<String, Object> searchMap);

    void deleteVideo(String videoNo);

    void insertVideo(MuflixDto muflixDto);

    MuflixDto getVideoDtl(String videoNo);

    void updateVideo(MuflixDto muflixDto);
}
