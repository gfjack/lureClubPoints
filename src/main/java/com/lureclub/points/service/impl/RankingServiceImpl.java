package com.lureclub.points.service.impl;

import com.lureclub.points.entity.points.PointsHistory;
import com.lureclub.points.entity.ranking.vo.response.RankingItemVo;
import com.lureclub.points.entity.user.User;
import com.lureclub.points.enums.PointsType;
import com.lureclub.points.repository.PointsHistoryRepository;
import com.lureclub.points.repository.UserRepository;
import com.lureclub.points.service.RankingService;
import com.lureclub.points.util.DateUtil;
import com.lureclub.points.util.ValidationUtil;
import com.lureclub.points.entity.user.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 排行榜服务实现类
 *
 * @author system
 * @date 2025-06-19
 */
@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private PointsHistoryRepository pointsHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private DateUtil dateUtil;

    /**
     * 获取当日排行榜实现
     */
    @Override
    public List<RankingItemVo> getDailyRanking() {
        LocalDate today = LocalDate.now();

        // 获取当日所有积分历史
        List<PointsHistory> todayHistory = pointsHistoryRepository.findAllByDateRange(today, today);

        // 按用户分组并计算当日积分
        Map<Long, Integer> userDailyPoints = new HashMap<>();
        for (PointsHistory history : todayHistory) {
            if (history.getPointsType() == PointsType.EARNED) {
                userDailyPoints.merge(history.getUserId(), history.getPoints(), Integer::sum);
            }
        }

        return buildRankingList(userDailyPoints);
    }

    /**
     * 获取本周排行榜实现
     */
    @Override
    public List<RankingItemVo> getWeeklyRanking() {
        // 计算本周开始和结束日期
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

        // 获取本周所有积分历史
        List<PointsHistory> weekHistory = pointsHistoryRepository.findAllByDateRange(weekStart, weekEnd);

        // 按用户分组并计算本周积分
        Map<Long, Integer> userWeeklyPoints = new HashMap<>();
        for (PointsHistory history : weekHistory) {
            if (history.getPointsType() == PointsType.EARNED) {
                userWeeklyPoints.merge(history.getUserId(), history.getPoints(), Integer::sum);
            }
        }

        return buildRankingList(userWeeklyPoints);
    }

//    /**
//     * 获取本周排行榜实现（使用DateUtil）
//     */
//    @Override
//    public List<RankingItemVo> getWeeklyRanking() {
//        // 使用DateUtil获取本周日期范围
//        LocalDate weekStart = DateUtil.getWeekStart();
//        LocalDate weekEnd = DateUtil.getWeekEnd();
//
//        // 获取本周所有积分历史
//        List<PointsHistory> weekHistory = pointsHistoryRepository.findAllByDateRange(weekStart, weekEnd);
//
//        // 按用户分组并计算本周积分
//        Map<Long, Integer> userWeeklyPoints = new HashMap<>();
//        for (PointsHistory history : weekHistory) {
//            if (history.getPointsType() == PointsType.EARNED) {
//                userWeeklyPoints.merge(history.getUserId(), history.getPoints(), Integer::sum);
//            }
//        }
//
//        return buildRankingList(userWeeklyPoints);
//    }

    /**
     * 获取总排行榜实现
     */
    @Override
    public List<RankingItemVo> getTotalRanking() {
        // 获取所有用户的总积分
        List<User> allUsers = userRepository.findAllActiveUsers();
        Map<Long, Integer> userTotalPoints = new HashMap<>();

        for (User user : allUsers) {
            Integer totalPoints = pointsHistoryRepository.calculateTotalPoints(user.getId());
            if (totalPoints != null && totalPoints > 0) {
                userTotalPoints.put(user.getId(), totalPoints);
            }
        }

        return buildRankingList(userTotalPoints);
    }

    /**
     * 构建排行榜列表
     *
     * @param userPointsMap 用户积分映射
     * @return 排行榜列表
     */
    private List<RankingItemVo> buildRankingList(Map<Long, Integer> userPointsMap) {
        if (userPointsMap.isEmpty()) {
            return new ArrayList<>();
        }

        // 按积分降序排序
        List<Map.Entry<Long, Integer>> sortedEntries = userPointsMap.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // 构建排行榜
        List<RankingItemVo> rankingList = new ArrayList<>();
        Map<Long, User> userMap = getUserMap(userPointsMap.keySet());

        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<Long, Integer> entry = sortedEntries.get(i);
            Long userId = entry.getKey();
            Integer points = entry.getValue();

            User user = userMap.get(userId);
            if (user != null) {
                // 使用ValidationUtil脱敏手机号显示在排行榜中
                String maskedPhone = validationUtil.maskPhone(user.getPhone());
                RankingItemVo rankingItem = new RankingItemVo(
                        i + 1, // 排名从1开始
                        userId,
                        user.getUsername(),
                        points
                );
                rankingList.add(rankingItem);
            }
        }

        return rankingList;
    }

    /**
     * 获取用户信息映射
     *
     * @param userIds 用户ID集合
     * @return 用户信息映射
     */
    private Map<Long, User> getUserMap(Set<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        return users.stream()
                .filter(user -> user.getIsDeleted() == 0)
                .collect(Collectors.toMap(User::getId, user -> user));
    }

}