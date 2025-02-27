package org.software.code.service.strategy.strategies;

import org.software.code.service.strategy.RiskCalculationStrategy;
import org.springframework.stereotype.Component;

/**
 * RiskCalculationStrategy201912 是一个实现了风险计算策略接口的具体策略类。
 * 它代表了 2019 年 12 月版本的风险计算策略，根据给定区域的人口数量和确诊病例数来计算风险等级。
 * 该类被 Spring 框架识别为一个组件，会自动注册到 Spring 应用上下文中，方便在其他地方通过依赖注入使用。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class RiskCalculationStrategy201912 implements RiskCalculationStrategy {

    /**
     * 根据给定区域的人口数量和确诊病例数计算风险等级。
     * 计算方法是通过确诊病例数除以人口数量得到感染率，然后根据感染率的范围来确定风险等级。
     *
     * @param population 给定区域的人口数量
     * @param confirmedCases 给定区域的确诊病例数
     * @return 风险等级，可能的值为 "green"（低风险）、"yellow"（中风险）或 "red"（高风险）
     */
    @Override
    public String calculateRiskLevel(int population, int confirmedCases) {
        // 计算感染率，将确诊病例数除以人口数量
        double infectionRate = (double) confirmedCases / population;

        // 根据感染率的范围判断风险等级
        if (infectionRate <= 0.1) {
            // 感染率小于等于 0.1 时，风险等级为绿色（低风险）
            return "green";
        } else if (infectionRate <= 0.3) {
            // 感染率大于 0.1 且小于等于 0.3 时，风险等级为黄色（中风险）
            return "yellow";
        } else {
            // 感染率大于 0.3 时，风险等级为红色（高风险）
            return "red";
        }
    }
}