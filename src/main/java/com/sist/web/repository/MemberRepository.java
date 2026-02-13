package com.sist.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
	
	@Query("SELECT COUNT(*) FROM Member "
		 + "WHERE id = :id")
	public int memberIdCount(@Param("id") String id);
	
	// public int countById(String id)
	
	@Query("SELECT m FROM Member m "
		 + "WHERE id = :id")
	public Optional<Member> memberInfoData(@Param("id") String id);
	
	// public Optional<Member> queryById(String id)
	
	@Query("SELECT pwd FROM Member "
		 + "WHERE id = :id")
	public String memberGetPassword(@Param("id") String id);

}
