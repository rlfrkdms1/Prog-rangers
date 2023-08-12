package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, QueryDslProblemRepository {
    List<Problem> findAllByOrderByDateDesc();

    List<Problem> findAllByLink(String link);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.dataStructure = :dataStructure order by s.date desc")
    List<Problem> findByDataStructureSortByDate(@Param("dataStructure") DataStructureConstant dateStructure);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.algorithm= :algorithm order by s.date desc")
    List<Problem> findByAlgorithmSortByDate(@Param("algorithm") AlgorithmConstant algorithm);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id order by s.date desc")
    List<Problem> findSortByDate();


    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.dataStructure = :dataStructure group by p.id order by count(s.id) desc")
    List<Problem> findByDataStructureSortBySolution(@Param("dataStructure") DataStructureConstant dataStructure);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.algorithm= :algorithm group by p.id order by count(s.id) desc")
    List<Problem> findByAlgorithmSortBySolution(@Param("algorithm") AlgorithmConstant algorithm);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id  group by p.id order by count(s.id) desc")
    List<Problem> findSortBySolution();

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.algorithm= :algorithm and s.dataStructure = :dataStructure order by s.date desc")
    List<Problem> findByAlgorithmAndDataStructureSortByDate(@Param("algorithm") AlgorithmConstant algorithm, @Param("dataStructure") DataStructureConstant dataStructure);

    @Query(value = "select  p from Problem p join Solution  s on p.id=s.problem.id where s.algorithm= :algorithm and s.dataStructure = :dataStructure group by p.id order by count(s.id) desc")
    List<Problem> findByAlgorithmAndDataStructureSortBySolution(@Param("algorithm") AlgorithmConstant algorithm, @Param("dataStructure") DataStructureConstant dataStructure);

    @Query(value = "select s.algorithm from Problem p join Solution s on p.id=s.problem.id where p.id= :problemId group by s.algorithm order by count(s.algorithm) desc limit 3")
    List<AlgorithmConstant> getTopAlgorithms(@Param("problemId") Long problemId);

    @Query(value = "select s.dataStructure from Problem p join Solution s on p.id=s.problem.id where p.id= :problemId group by s.dataStructure order by count(s.dataStructure) desc limit 3")
    List<DataStructureConstant> getTopDataStructures(@Param("problemId") Long problemId);
}
