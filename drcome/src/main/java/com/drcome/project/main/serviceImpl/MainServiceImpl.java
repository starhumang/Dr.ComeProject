package com.drcome.project.main.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.main.mapper.MainMapper;
import com.drcome.project.main.service.MainService;
import com.drcome.project.main.service.ReservationVO;
import com.drcome.project.medical.service.DoctorVO;
import com.drcome.project.medical.service.HospitalVO;
import com.drcome.project.pharmacy.PharmacyVO;

@Service
public class MainServiceImpl implements MainService {

   @Autowired
   MainMapper mainMapper;

   @Override
   public List<HospitalVO> getHosList() {
      return mainMapper.selectHosList();
   }

   @Override
   public List<PharmacyVO> getPhaList() {
      return mainMapper.selectPhaList();
   }

   @Override
   public HospitalVO getHos(String hospitalId) {
      return mainMapper.selectHos(hospitalId);
   }

   @Override
   public PharmacyVO getPha(String pharmacyId) {
      return mainMapper.selectPha(pharmacyId);
   }

   @Override
   public List<HospitalVO> searchHosList(String word) {
      return mainMapper.searchHosList(word);
   }

   @Override
   public List<PharmacyVO> searchPhaList(String word) {
      return mainMapper.searchPhaList(word);
   }
   @Override
//   @Transactional 컨트롤러에서 해야하는거 여기서 할때 문제 생기면 롤백
   public List<HospitalVO> searchSubjectHos(String mainSubject) {
      return mainMapper.searchSubjectHos(mainSubject);
   }
   
	@Override
	public List<PharmacyVO> recommendPhaList(String clinicNo,int num) {
		return mainMapper.recommendPhaList(clinicNo,num);
	}

	@Override
	public int insertPhaSelect(String pharmacyId, int clinicNo) {
		return mainMapper.insertPhaSelect(pharmacyId, clinicNo);
		//0아니면 1이상의 값이 리턴
	}

	@Override
	public int checkClinicHistory(String userId, String hospitalId) {
		int clinicHistory = mainMapper.checkClinicHistory(userId, hospitalId);
		return clinicHistory;
	}

	@Override
	public int checkReservationHistory(String userId, String hospitalId) {
		int reservationHistory = mainMapper.checkReservationHistory(userId, hospitalId);
		return reservationHistory;
	}

	@Override
	public int insertContactReservation(ReservationVO reservationVo) {
		int result = mainMapper.insertContactReservation(reservationVo);
		return result;
	}
	
	@Override
	public int insertUntactReservation(ReservationVO reservationVo) {
		int result = mainMapper.insertUntactReservation(reservationVo);
		return result;
	}

	@Override
	public List<ReservationVO> findreserveListToChoice(ReservationVO reservationVo) {
		return mainMapper.findreserveListToChoice(reservationVo);
	}

	@Override
	public List<ReservationVO> findWaitingList(DoctorVO doctorVO) {
		return mainMapper.findWaitingList(doctorVO);
	}



	

	






}

