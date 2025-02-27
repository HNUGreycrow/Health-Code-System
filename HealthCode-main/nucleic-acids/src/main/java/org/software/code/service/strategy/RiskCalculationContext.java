package org.software.code.service.strategy;

import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RiskCalculationContext 是一个风险计算上下文类，它基于策略模式来管理和使用不同的风险计算策略。
 * 该类负责维护所有可用的风险计算策略，并根据需求选择合适的策略进行风险等级计算。
 * 作为 Spring 组件，它会被自动扫描并注册到 Spring 应用上下文中。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class RiskCalculationContext {

    /**
     * 存储所有可用的风险计算策略，键为策略类的简单名称，值为对应的策略实例。
     */
    private final Map<String, RiskCalculationStrategy> strategies;

    /**
     * 构造函数，通过依赖注入的方式接收所有实现了 RiskCalculationStrategy 接口的策略列表。
     * 并将这些策略实例存储到一个 Map 中，键为策略类的简单名称，方便后续根据名称查找策略。
     *
     * @param strategyList 所有实现了 RiskCalculationStrategy 接口的策略列表
     */
    @Autowired
    public RiskCalculationContext(List<RiskCalculationStrategy> strategyList) {
        // 使用 Java 8 的 Stream API 将策略列表转换为 Map，键为策略类的简单名称，值为策略实例
        strategies = strategyList.stream().collect(Collectors.toMap(strategy -> strategy.getClass().getSimpleName(), strategy -> strategy));
        // 打印所有策略，方便调试查看
        System.out.println(strategies);
    }

    /**
     * 根据给定的人口数量和确诊病例数计算风险等级。
     * 该方法会获取最新的风险计算策略，并调用该策略的 calculateRiskLevel 方法进行计算。
     *
     * @param population 给定区域的人口数量
     * @param confirmedCases 给定区域的确诊病例数
     * @return 计算得到的风险等级
     * @throws BusinessException 如果未找到合适的风险计算策略，抛出业务异常
     */
    public String calculateRiskLevel(int population, int confirmedCases) {
        // 获取最新的风险计算策略
        RiskCalculationStrategy strategy = getLatestStrategy();
        // 如果未找到合适的策略，抛出业务异常
        if (strategy == null) {
            throw new BusinessException(ExceptionEnum.RISK_CALCULATION_NOT_FIND);
        }
        // 调用策略的 calculateRiskLevel 方法计算风险等级
        return strategy.calculateRiskLevel(population, confirmedCases);
    }

    /**
     * 获取最新的风险计算策略。
     * 目前该方法硬编码了最新策略的名称为 "RiskCalculationStrategy202006"，
     * 可以根据实际需求修改为动态获取最新策略的逻辑。
     *
     * @return 最新的风险计算策略实例，如果未找到则返回 null
     */
    private RiskCalculationStrategy getLatestStrategy() {
        // 设置最新策略的名称
        String latestStrategyName = "RiskCalculationStrategy202006";
        // 根据策略名称从 Map 中获取对应的策略实例
        return strategies.get(latestStrategyName);
    }
}