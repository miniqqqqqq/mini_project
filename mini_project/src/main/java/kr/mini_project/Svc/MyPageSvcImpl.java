package kr.mini_project.Svc;

import kr.mini_project.Dao.MyPageDao;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.TicketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageSvcImpl implements MyPageSvc{

    @Autowired
    MyPageDao mypageDao;

    @Override
    public List<MypageDto> getPlayList(MypageDto mypageDto) {
        return mypageDao.getPlayList(mypageDto);
    }

    @Override
    public void insertLike(MypageDto mypageDto) {
        mypageDao.insertLike(mypageDto);
    }

    @Override
    public void deleteLike(MypageDto mypageDto) {
        mypageDao.deleteLike(mypageDto);
    }

    @Override
    public List<MypageDto> getLikesList(MypageDto mypageDto) {
        return mypageDao.getLikesList(mypageDto);
    }

    @Override
    public List<TicketDto> getTicketList(String userId) {
        return mypageDao.getTicketList(userId);
    }

    @Override
    public void insertBuyUser(TicketDto ticketDto) {
        mypageDao.insertBuyUser(ticketDto);
    }

    @Override
    public void updateUserTickNo(TicketDto ticketDto) {
        mypageDao.updateUserTickNo(ticketDto);
    }

    @Override
    public List<TicketDto> getTicketBuyDtl(String ticketNo) {
        return mypageDao.getTicketBuyDtl(ticketNo);
    }

    @Override
    public String getMyTicket(TicketDto ticketDto) {
        return mypageDao.getMyTicket(ticketDto);
    }

    @Override
    public List<MypageDto> getPlayListDtl(MypageDto mypageDto) {
        return mypageDao.getPlayListDtl(mypageDto);
    }

    @Override
    public int getMyPlayListCnt(MypageDto mypageDto) {
        return mypageDao.getMyPlayListCnt(mypageDto);
    }

    @Override
    public void deletePlayList(MypageDto mypageDto) {
        mypageDao.deletePlayList(mypageDto);
    }

    @Override
    public int getMaxPlayNo() {
        return mypageDao.getMaxPlayNo();
    }

    @Override
    public void insertPlayList(MypageDto mypageDto) {
        mypageDao.insertPlayList(mypageDto);
    }

    @Override
    public void insertComment(MypageDto mypageDto) {
        mypageDao.insertComment(mypageDto);
    }

    @Override
    public List<MypageDto> selectComment(MypageDto mypageDto) {
        return mypageDao.selectComment(mypageDto);
    }

    @Override
    public List<MypageDto> getMyCommList(MypageDto mypageDto) {
        return mypageDao.getMyCommList(mypageDto);
    }

    @Override
    public void insertCommReport(MypageDto mypageDto) {
        mypageDao.insertCommReport(mypageDto);
    }

    @Override
    public int selectMyRepoCnt(MypageDto mypageDto) {
        return mypageDao.selectMyRepoCnt(mypageDto);
    }

    @Override
    public int myCommentChk(MypageDto mypageDto) {
        return mypageDao.myCommentChk(mypageDto);
    }

    @Override
    public void updateCommentDel(MypageDto mypageDto) {
        mypageDao.updateCommentDel(mypageDto);
    }

    @Override
    public List<MypageDto> getMyBuyTicketList(MypageDto mypageDto) {
        return mypageDao.getMyBuyTicketList(mypageDto);
    }

    @Override
    public TicketDto getMyticketDttm(TicketDto ticketDto) {
        return mypageDao.getMyticketDttm(ticketDto);
    }

    @Override
    public void updateTicketEndDttm(TicketDto ticketDto) {
        mypageDao.updateTicketEndDttm(ticketDto);
    }

}
