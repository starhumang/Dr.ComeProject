package com.drcome.project.doctor.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.common.service.AlarmDao;
import com.drcome.project.common.service.AlarmService;
import com.drcome.project.common.service.PageDTO;
import com.drcome.project.doctor.mapper.PatientMapper;
import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;
import com.drcome.project.medical.service.HospitalService;

/**
 * 
 * 환자 진료 컨트롤러 클래스
 * 
 * @author 이주은
 */

@Controller
public class PatientController {

	@Autowired
	HospitalService hospitalService;

	@Autowired
	PatientService patientService;

	@Autowired
	AlarmService alarmService;

	@Autowired
	PatientMapper mapper;

	/**
	 * 공통 정보 : 병원 
	 * @param principal 병원 아이디 
	 * @return hosSel 
	 */
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime(Principal principal) {
		String hospitalId = principal.getName();
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		return hosSel;
	}
	

	/**
	 * 의사 알람 테이블 인서트 + 입장하기 상태로 업데이트 진행
	 * 
	 * @param dao (reserveNo , userId , contentCode , prefix)
	 * @return int 저장건수 
	 */
	@PostMapping("saveAlarm")
	@ResponseBody
	public int saveAlarm(@RequestBody AlarmDao dao) {

		return alarmService.saveAlarm(dao);

	}

	/**
	 * 비대면 진료페이지 의사화면
	 * 
	 * @param vo reserveNo
	 * @return doctor/untactClinic 비대면 진료 페이지
	 */
	@GetMapping("untactClinic")
	public String getUntactInfo(PatientVO vo, Model model) {

		// 기본정보 조회 reserveNo로
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo", findVO);

		return "doctor/untactClinic";
	}

	/**
	 * 대면 진료 페이지
	 * 
	 * @param vo    reserveNo
	 * @param model
	 * @return doctor/clinic 대면 진료 페이지
	 */
	@GetMapping("clinic")
	public String getInfo(PatientVO vo, Model model) {

		// 기본정보 조회 reserveNo로
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo", findVO);
		return "doctor/clinic";

	}

	/**
	 * 진료기록 불러오기 + 페이징
	 * 
	 * @param principal 병원아이디
	 * @param page      선택한 페이지
	 * @param uid       환자아이디
	 * @return map (list,dto)
	 */
	@GetMapping("clist")
	@ResponseBody
	public Map<String, Object> clinicList(Principal principal, @RequestParam(required = false, defaultValue = "1") int page, String uid) {   //초기값 설정가능

		PatientVO vo = new PatientVO();

		// 환자아이디
		vo.setUserId(uid);
		// 병원아이디
		vo.setHospitalId(principal.getName());

		
		// 전체갯수 가져오기
		int total = patientService.totalList(vo);

		// 선택된 페이지 인트로 변환
		//int cpage = Integer.parseInt(page);

		// 페이지네이션
		PageDTO dto = new PageDTO(page, total);

		// 진료기록리스트 (vo : userId hospitalId)
		List<PatientVO> clinicList = patientService.getClinicList(page, vo);

		Map<String, Object> map = new HashMap<>();
		map.put("list", clinicList); // 리스트 넘기고
		map.put("dto", dto); // 페이지 정보담긴 애도 보냄

		return map;

	}

	/**
	 * 처방전 조회
	 * 
	 * @param no clinicNo
	 * @return List<PatientVO>
	 */
	@GetMapping("/perscription/{no}")
	@ResponseBody
	public List<PatientVO> perscriptionInfo(@PathVariable Integer no) {
		PatientVO vo = new PatientVO();
		vo.setClinicNo(no);

		return patientService.getPerscription(vo);
	}

	/**
	 * 약검색
	 * 
	 * @param vo medicineName
	 * @return patientService
	 */
	@GetMapping("medicine")
	@ResponseBody
	public List<PatientVO> msearch(PatientVO vo) {

		return patientService.getmnameList(vo);

	}

	/**
	 * 진료기록저장
	 * 
	 * @param reserveNo, clinicSymptom specificity payYn perscriptionYn hospitalId
	 *                   visit userId perary=[{dosage , doseCnt ,doseDay,
	 *                   medicineNo},{}]
	 * @return patientService
	 */

	@PostMapping("saveClinic")
	@ResponseBody
	public int saveInfo(@RequestBody PatientVO vo) {

		return patientService.insertClinic(vo);
	}

	/**
	 * 진료종료시 진료완료로 상태업데이트
	 * 
	 * @param vo reserveNo
	 * @return patientService
	 */
	@PostMapping("/updateStatus")
	@ResponseBody
	public int updateStatus(PatientVO vo) {

		return patientService.modifyReserve(vo);
	}

	/**
	 * 대면진료 결제완료처리버튼 누를시 결제완료로 상태업데이트
	 * 
	 * @param vo reserveNo
	 * @return mapper
	 */
	@PostMapping("/updatePayment")
	@ResponseBody
	public int updatePayment(PatientVO vo) {

		return mapper.updatePayment(vo);
	}

}// end class