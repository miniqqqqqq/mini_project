package kr.mini_project.Svc;

import kr.mini_project.Dto.MuflixDto;
import kr.mini_project.Dto.MypageDto;
import kr.mini_project.Dto.joinDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeSvc {

    @Autowired
    private SqlSession sqlSession;

    public void insertUser(joinDto dto) {
        sqlSession.insert("homeMapper.insertUser", dto);
    }

    public int getLoginChk(joinDto dto) {
        return sqlSession.selectOne("homeMapper.getLoginChk", dto);
    }


    public List<MuflixDto> getMuflixList(Map<String, Object> searchMap) {
        return sqlSession.selectList("homeMapper.getMuflixList", searchMap);
    }

    public MuflixDto getOneVideo(MypageDto mypageDto) {
        return sqlSession.selectOne("homeMapper.getOneVideo", mypageDto);
    }

    public joinDto getTickBuyUser(String userId) {
        return sqlSession.selectOne("homeMapper.getTickBuyUser", userId);
    }
}
