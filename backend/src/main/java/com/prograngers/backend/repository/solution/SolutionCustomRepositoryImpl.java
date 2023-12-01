package com.prograngers.backend.repository.solution;

import static com.prograngers.backend.entity.QFollow.follow;
import static com.prograngers.backend.entity.QLikes.likes;
import static com.prograngers.backend.entity.solution.QSolution.solution;
import static com.prograngers.backend.entity.sortconstant.SortConstant.LIKES;
import static com.prograngers.backend.entity.sortconstant.SortConstant.NEWEST;
import static com.prograngers.backend.entity.sortconstant.SortConstant.SCRAPS;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.QSolution;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class SolutionCustomRepositoryImpl implements SolutionCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Solution findOneRecentSolutionByMemberId(Long memberId) {
        return jpaQueryFactory
                .selectFrom(solution)
                .where(solution.member.id.eq(memberId))
                .orderBy(solution.createdAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public PageImpl<Solution> getSolutionList(
            Pageable pageable, Long problemId, LanguageConstant language,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure, SortConstant sortBy) {
        if (sortBy.equals(NEWEST)) {
            return getSolutionsSorByNewest(pageable, problemId, language, algorithm, dataStructure);
        }
        if (sortBy.equals(LIKES)) {
            return getSolutionsSortByLikes(pageable, problemId, language, algorithm, dataStructure);
        }
        if (sortBy.equals(SCRAPS)) {
            return getSolutionsSortByScraps(pageable, problemId, language, algorithm, dataStructure);
        }
        return null;
    }

    @Override
    public List<Solution> findProfileSolutions(Long memberId, Long page) {
        return jpaQueryFactory
                .select(solution)
                .from(solution)
                .where(solution.member.id.eq(memberId), solution.id.loe(page))
                .orderBy(solution.createdAt.desc())
                .limit(3)
                .fetch();
    }


    public List<Solution> findTopLimitsSolutionOfProblemOrderByLikesDesc(Problem problem, int limit) {
        return jpaQueryFactory
                .select(solution)
                .from(likes)
                .rightJoin(likes.solution, solution)
                .groupBy(solution)
                .where(solution.problem.eq(problem))
                .orderBy(likes.count().desc(), solution.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Solution> findMyRecentSolutions(Long memberId, int limit) {
        return jpaQueryFactory.selectFrom(solution)
                .where(solution.member.id.eq(memberId))
                .orderBy(solution.createdAt.desc())
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Solution> findFollowingsRecentSolutions(Long memberId, int limit) {
        return jpaQueryFactory.selectFrom(solution)
                .join(follow).on(solution.member.id.eq(follow.followingId))
                .join(solution.problem).fetchJoin()
                .where(follow.followerId.eq(memberId))
                .orderBy(solution.createdAt.desc())
                .limit(limit)
                .fetch();
    }


    @Override
    public Page<Solution> getMyList(Pageable pageable, String keyword, LanguageConstant language,
                                    AlgorithmConstant algorithm, DataStructureConstant dataStructure, Integer level,
                                    Long memberId) {
        JPAQuery<Solution> contentQuery = jpaQueryFactory.selectFrom(solution).join(solution.problem).fetchJoin();
        JPAQuery<Long> countQuery = jpaQueryFactory.select(solution.count()).from(solution);
        List<Solution> content = findByConditions(contentQuery, keyword, language, algorithm, dataStructure, level,
                memberId)
                .orderBy(solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long count = findByConditions(countQuery, keyword, language, algorithm, dataStructure, level,
                memberId).fetchOne();
        return count == null ? Page.empty() : new PageImpl<>(content, pageable, count);
    }

    private <T> JPAQuery<T> findByConditions(JPAQuery<T> query, String keyword, LanguageConstant language,
                                             AlgorithmConstant algorithm, DataStructureConstant dataStructure,
                                             Integer level, Long memberId) {
        return query.where(solution.member.id.eq(memberId),
                keywordEq(keyword),
                languageEq(language),
                algorithmEq(algorithm),
                dataStructureEq(dataStructure),
                levelEq(level));
    }

    private BooleanExpression levelEq(Integer level) {
        return level != null ? solution.level.eq(level) : null;
    }

    private BooleanExpression keywordEq(String keyword) {
        return keyword != null ? solution.title.contains(keyword) : null;
    }

    private BooleanExpression dataStructureEq(DataStructureConstant dataStructure) {
        return dataStructure != null ? solution.dataStructure.eq(dataStructure) : null;
    }

    private BooleanExpression algorithmEq(AlgorithmConstant algorithm) {
        return algorithm != null ? solution.algorithm.eq(algorithm) : null;
    }

    private BooleanExpression languageEq(LanguageConstant language) {
        return language != null ? solution.language.eq(language) : null;
    }

    private static BooleanExpression solutionEqProblemId(Long problemId) {
        return solution.problem.id.eq(problemId);
    }

    private static BooleanExpression solutionPublic() {
        return solution.isPublic.eq(true);
    }

    private List<Solution> getScrapsSolutions(Pageable pageable, Long problemId, LanguageConstant language,
                                              AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        QSolution subSolution = new QSolution("subSolution");
        result = jpaQueryFactory
                .select(solution)
                .from(subSolution)
                .rightJoin(subSolution.scrapSolution, solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm),
                        dataStructureEq(dataStructure))
                .groupBy(solution.id)
                .orderBy(subSolution.count().desc(), solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private List<Solution> getLikesSolutions(Pageable pageable, Long problemId, LanguageConstant language,
                                             AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = jpaQueryFactory
                .select(solution)
                .from(likes)
                .rightJoin(likes.solution, solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm),
                        dataStructureEq(dataStructure))
                .groupBy(solution.id)
                .orderBy(likes.id.count().desc(), solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private List<Solution> getNewestSolutions(Pageable pageable, Long problemId, LanguageConstant language,
                                              AlgorithmConstant algorithm, DataStructureConstant dataStructure) {
        List<Solution> result;
        result = jpaQueryFactory
                .selectFrom(solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm),
                        dataStructureEq(dataStructure))
                .orderBy(solution.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return result;
    }

    private Long getCount(Long problemId, LanguageConstant language, AlgorithmConstant algorithm,
                          DataStructureConstant dataStructure) {
        Long count = jpaQueryFactory
                .select(solution.count())
                .from(solution)
                .where(solutionPublic(), solutionEqProblemId(problemId), languageEq(language), algorithmEq(algorithm),
                        dataStructureEq(dataStructure))
                .fetchOne();
        return count;
    }

    private PageImpl<Solution> getSolutionsSortByScraps(Pageable pageable, Long problemId, LanguageConstant language,
                                                        AlgorithmConstant algorithm,
                                                        DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getScrapsSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }

    private PageImpl<Solution> getSolutionsSortByLikes(Pageable pageable, Long problemId, LanguageConstant language,
                                                       AlgorithmConstant algorithm,
                                                       DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getLikesSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }

    private PageImpl<Solution> getSolutionsSorByNewest(Pageable pageable, Long problemId, LanguageConstant language,
                                                       AlgorithmConstant algorithm,
                                                       DataStructureConstant dataStructure) {
        List<Solution> result;
        result = getNewestSolutions(pageable, problemId, language, algorithm, dataStructure);
        Long count = getCount(problemId, language, algorithm, dataStructure);
        PageImpl<Solution> solutions = new PageImpl<>(result, pageable, count);
        return solutions;
    }
}
