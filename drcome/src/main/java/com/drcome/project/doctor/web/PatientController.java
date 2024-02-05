package com.drcome.project.doctor.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.admin.domain.Hospital;
import com.drcome.project.doctor.service.PageDTO;
import com.drcome.project.doctor.service.PatientService;
import com.drcome.project.doctor.service.PatientVO;
import com.drcome.project.medical.service.HospitalService;

@Controller
public class PatientController {

	@Autowired
//	HospitalRepository repo;
	HospitalService hospitalService;

	@Autowired
	PatientService patientService;

	// 공통 병원 정보 따로 빼기
	@ModelAttribute("hospitalSel")
	public Hospital getServerTime() {
		String hospitalId = "krrlo";
		Hospital hosSel = hospitalService.findByhospitalId(hospitalId);
		return hosSel;
	}

	// 비대면진료페이지//reserve no 받아옴 /hid (세션) /uid (pinfo안) 받아와야함
	@GetMapping("untactClinic")
	public String getUntactInfo(PatientVO vo, Model model) {

		System.out.println("너뭐야 " + vo); // 아무것도없음

		// 기본정보
		vo.setReserveNo(4);
		// System.out.println("너는뭐야 "+ vo); //reserveno만 4로 찍힘

		PatientVO findVO = patientService.getPatientInfo(vo);
		// System.out.println("findvo" + findVO);
		// (reserveNo=4, userName=이주은, gender=F, birthday=Wed Sep 07 00:00:00 KST 1994,
		// symptom=배가아픔, clinicNo=0, clinicSymptom=null, specificity=null, payYn=null,
		// paymentNo=0, perscriptionYn=null, clinicDate=null, hospitalId=null,
		// patientNo=0, perscriptionNo=0, dosage=0, doseCnt=0, doseDay=0,
		// perscriptionDate=null, medicineName=null, medicineNo=0, userId=user1,
		// currentDate=null, visit=null, perary=null, page=null)
		model.addAttribute("pInfo", findVO);

		return "doctor/untactClinic";

	}

	// 진료기록 불러오기
	@GetMapping("clist")
	@ResponseBody
	public Map<String, Object> clinicList(String page, String uid) {

		System.out.println(page + uid);
		PatientVO vo = new PatientVO();

		vo.setUserId(uid);
		vo.setHospitalId("krrlo");

		// 리스트 전체갯수 가져오기
		int total = patientService.totalList(vo);
		System.out.println("토탈" + total);

		// 선택된 페이지 인트로 변환
		int cpage = Integer.parseInt(page);
		System.out.println("선택된페이지" + cpage);

		// 페이지네이션(currentpage, total)
		PageDTO dto = new PageDTO(cpage, total);
		System.out.println("dtd 객체 " + dto);

		// 진료기록리스트
		List<PatientVO> clinicList = patientService.getClinicList(cpage, vo);

		System.out.println(clinicList.size());

		Map<String, Object> map = new HashMap<>();
		map.put("list", clinicList); // 댓글리스트 넘기고
		map.put("dto", dto); // 페이지 정보담긴 애도 보냄

		return map;

	}

	// 대면진료페이지
	@GetMapping("clinic")
	public String getInfo(PatientVO vo, Model model) {
		PatientVO findVO = patientService.getPatientInfo(vo);
		model.addAttribute("pInfo", findVO);
		// System.out.println(findVO);
		return "doctor/clinic";

	}

	// 처방전 조회
	@GetMapping("perscription/{no}")
	@ResponseBody
	public List<PatientVO> perscriptionInfo(@PathVariable Integer no) {
		PatientVO vo = new PatientVO();
		vo.setClinicNo(no);
		// System.out.println(vo);
		return patientService.getPerscription(vo);
	}

	// 약검색
	@GetMapping("medicine")
	@ResponseBody
	public List<PatientVO> msearch(PatientVO vo) {
		// System.out.println(vo);
		return patientService.getmnameList(vo);

	}

	// 진료기록 저장
	@PostMapping("saveClinic")
	@ResponseBody
	public int saveInfo(@RequestBody PatientVO vo) {
		return patientService.insertClinic(vo);
	}

}// end class
