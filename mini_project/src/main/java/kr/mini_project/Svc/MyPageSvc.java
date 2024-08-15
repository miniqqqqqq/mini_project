package kr.mini_project.Svc;

import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;

import java.util.List;

public interface MyPageSvc {
    List<MypageDto> getPlayList(MypageDto mypageDto);

    void insertLike(MypageDto mypageDto);

    void deleteLike(MypageDto mypageDto);

    List<MypageDto> getLikesList(MypageDto mypageDto);

    List<TicketDto> getTicketList(String userId);

    void insertBuyUser(TicketDto ticketDto);

    void updateUserTickNo(TicketDto ticketDto);

    List<TicketDto> getTicketBuyDtl(String ticketNo);

    String getMyTicket(TicketDto ticketDto);

    List<MypageDto> getPlayListDtl(MypageDto mypageDto);

    int getMyPlayListCnt(MypageDto mypageDto);

    void deletePlayList(MypageDto mypageDto);

    int getMaxPlayNo();

    void insertPlayList(MypageDto mypageDto);

    void insertComment(MypageDto mypageDto);

    List<MypageDto> selectComment(MypageDto mypageDto);

    List<MypageDto> getMyCommList(MypageDto mypageDto);

    void insertCommReport(MypageDto mypageDto);

    int selectMyRepoCnt(MypageDto mypageDto);

    int myCommentChk(MypageDto mypageDto);

    void updateCommentDel(MypageDto mypageDto);

    List<MypageDto> getMyBuyTicketList(MypageDto mypageDto);

    TicketDto getMyticketDttm(TicketDto ticketDto);

    void updateTicketEndDttm(TicketDto ticketDto);

}
