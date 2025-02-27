package org.software.code.service.strategy;

/**
 * RiskCalculationStrategy 是一个策略接口，用于定义风险计算的策略标准。
 * 此接口基于策略模式设计，允许根据不同的业务需求和场景，实现多种不同的风险计算策略。
 * 不同的实现类可以根据特定的规则和算法，根据给定的人口数量和确诊病例数计算出对应的风险等级。
 * 这样设计提高了代码的可扩展性和可维护性，方便后续添加或修改风险计算策略。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface RiskCalculationStrategy {
    /**
     * 根据给定区域的人口数量和确诊病例数计算该区域的风险等级。
     * 不同的实现类会有不同的计算逻辑，例如根据感染率的不同范围划分不同的风险等级。
     *
     * @param population 给定区域的人口数量
     * @param confirmedCases 给定区域的确诊病例数
     * @return 计算得出的风险等级，通常以字符串形式表示，如 "green"（低风险）、"yellow"（中风险）、"red"（高风险）等
     */
    String calculateRiskLevel(int population, int confirmedCases);
}