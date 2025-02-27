package org.software.code.service.strategy.strategies;

import org.software.code.service.strategy.RiskCalculationStrategy;
import org.springframework.stereotype.Component;

/**
 * RiskCalculationStrategy202006 是一个具体的风险计算策略类，实现了 RiskCalculationStrategy 接口。
 * 该类代表了 2020 年 6 月所采用的风险计算策略，依据指定区域的人口数量和确诊病例数来计算风险等级。
 * 作为 Spring 组件，它会被自动扫描并注册到 Spring 应用上下文中，方便通过依赖注入的方式使用。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class RiskCalculationStrategy202006 implements RiskCalculationStrategy {

    /**
     * 根据给定区域的人口数量和确诊病例数来计算该区域的风险等级。
     * 具体计算方式是先算出确诊病例数在人口总数中的占比（即感染率），
     * 再根据感染率所处的区间来确定对应的风险等级。
     *
     * @param population 该区域的人口总数
     * @param confirmedCases 该区域的确诊病例数
     * @return 计算得出的风险等级，可能的返回值为 "green"（低风险）、"yellow"（中风险）或 "red"（高风险）
     */
    @Override
    public String calculateRiskLevel(int population, int confirmedCases) {
        // 计算感染率，将确诊病例数除以人口总数
        double infectionRate = (double) confirmedCases / population;

        // 根据感染率的不同范围确定风险等级
        if (infectionRate <= 0.3) {
            // 若感染率小于等于 0.3，判定为低风险，返回 "green"
            return "green";
        } else if (infectionRate <= 0.4) {
            // 若感染率大于 0.3 且小于等于 0.4，判定为中风险，返回 "yellow"
            return "yellow";
        } else {
            // 若感染率大于 0.4，判定为高风险，返回 "red"
            return "red";
        }
    }
}