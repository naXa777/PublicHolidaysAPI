package com.avk.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/copyright")
public class CopyrightController
{
	@GetMapping
	public ResponseEntity<List<String>> getCopyright() throws RuntimeException
	{
		List<String> result = new ArrayList<>();
		result.add("New Zealand public holidays were a derivative of work found on https://www.govt.nz/ licenced under a Creative Commons 4.0 International Licence, https://creativecommons.org/licenses/by/4.0/");
		result.add("Australian public holidays were a derivative of work found on http://www.australia.gov.au/ licenced under a Creative Commons Attribution 3.0 Australia Licence, https://creativecommons.org/licenses/by/3.0/au/");
		result.add("Singapore public holidays were a derivative of work found on http://www.australia.gov.au/ licenced under a Creative Commons Attribution 3.0 Australia Licence, https://creativecommons.org/licenses/by/3.0/au/");
		result.add("Hong Kong public holidays are gazetted for public information, subject to copyright owned by the Government of the Hong Kong Special Administrative Region (\"Government\"). Source information from \"GovHK (www.gov.hk)\".");
		
		return new ResponseEntity<List<String>>(result, HttpStatus.OK);
	}
}
