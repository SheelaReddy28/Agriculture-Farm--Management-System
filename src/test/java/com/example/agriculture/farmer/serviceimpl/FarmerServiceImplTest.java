package com.example.agriculture.farmer.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.agriculture.farmer.dto.FarmerResponseDto;
import com.example.agriculture.farmer.entity.Farmer;
import com.example.agriculture.farmer.mapper.FarmerMapper;
import com.example.agriculture.farmer.repository.FarmerRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.agriculture.exception.UserDefineException;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.ArgumentMatchers;
import com.example.agriculture.farmer.dto.FarmerRequestDto;

@ExtendWith(MockitoExtension.class)
public class FarmerServiceImplTest 
{

	@Mock
	private FarmerRepository farmerRepository;

	@Mock
	private FarmerMapper farmerMapper;

	@InjectMocks
	private FarmerServiceImpl farmerService;

	private Farmer farmer;
	private FarmerResponseDto responseDto;

	@BeforeEach
	void setUp() {
		farmer = new Farmer();
		farmer.setFarmerId(1);
		farmer.setFarmerName("Ravi");
		farmer.setEmail("ravi@gmail.com");

		responseDto = new FarmerResponseDto();
		responseDto.setFarmerId(1);
		responseDto.setFarmerName("Ravi");
		responseDto.setEmail("ravi@gmail.com");
	}

	@Test
	void testGetById_Success() {
		when(farmerRepository.findById(1)).thenReturn(Optional.of(farmer));

		when(farmerMapper.toResponseDto(farmer)).thenReturn(responseDto);

		FarmerResponseDto result = farmerService.getById(1);

		assertNotNull(result);
		assertEquals(1, result.getFarmerId());
		assertEquals("Ravi", result.getFarmerName());
		assertEquals("ravi@gmail.com", result.getEmail());

		verify(farmerRepository).findById(1);
		verify(farmerMapper).toResponseDto(farmer);
	}
	
	@Test
	void testGetById_NotFound() 
	{
	    when(farmerRepository.findById(99))
	            .thenReturn(Optional.empty());

	    assertThrows(
	            UserDefineException.class,
	            () -> farmerService.getById(99)
	    );

	    verify(farmerRepository).findById(99);
}
	@Test
	void testCreateOrUpdate_Success() {

	    FarmerRequestDto requestDto = new FarmerRequestDto();
	    requestDto.setFarmerName("Ravi");
	    requestDto.setEmail("ravi@gmail.com");

	    when(farmerRepository.existsByEmail("ravi@gmail.com"))
	            .thenReturn(false);

	    when(farmerMapper.toEntity(requestDto))
	            .thenReturn(farmer);

	    when(farmerRepository.save(any(Farmer.class)))
	            .thenReturn(farmer);

	    when(farmerMapper.toResponseDto(farmer))
	            .thenReturn(responseDto);

	    FarmerResponseDto result =
	            farmerService.createOrUpdate(requestDto);

	    assertNotNull(result);
	    assertEquals("Ravi", result.getFarmerName());
	    assertEquals("ravi@gmail.com", result.getEmail());

	    verify(farmerRepository).save(farmer);
	}
	
	@Test
	void testCreateOrUpdate_DuplicateEmail() {

	    FarmerRequestDto requestDto = new FarmerRequestDto();
	    requestDto.setFarmerName("Ravi");
	    requestDto.setEmail("ravi@gmail.com");

	    when(farmerRepository.existsByEmail("ravi@gmail.com"))
	            .thenReturn(true);

	    assertThrows(
	            IllegalArgumentException.class,
	            () -> farmerService.createOrUpdate(requestDto)
	    );

	    verify(farmerRepository).existsByEmail("ravi@gmail.com");
	}
	

@Test
void testGetFarmerCount() {

    when(farmerRepository.count()).thenReturn(8L);

    long result = farmerService.getFarmerCount();

    assertEquals(8L, result);

    verify(farmerRepository).count();
}

}
