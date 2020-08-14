package com.trade.security.http;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test/v1/controller")
public class ExampleTestController {

	@PostMapping("/java/string")
	public void save(String string) {
	}

	@PostMapping("/java/strings")
	public void saveAll(List<String> strings) {
	}

	@GetMapping("/java/string")
	public String findByStart(String start) {
		return "";
	}

	@GetMapping("/java/strings")
	public List<String> findAll() {
		return Arrays.asList("", "");
	}

	@GetMapping("/java/string/code/{code}")
	public String findByCode(String code) {
		return "";
	}

	@DeleteMapping("/java/string")
	public void delete(String stringId) {
	}
}
