package org.software.code.config;

import org.software.code.common.consts.FSMConst;
import org.software.code.common.consts.FSMConst.HealthCodeColor;
import org.software.code.common.consts.FSMConst.HealthCodeEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import java.util.EnumSet;

/**
 * StateMachineConfig 是一个配置类，用于配置 Spring State Machine，
 * 该配置类主要用于管理健康码状态机的状态和状态转换规则。
 * Spring State Machine 是一个强大的状态机框架，可用于实现复杂的状态转换逻辑。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Configuration
// 启用状态机工厂，允许 Spring 创建状态机工厂实例，用于生成状态机对象
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<HealthCodeColor, HealthCodeEvent> {

    /**
     * 创建一个 StateMachineService Bean，用于管理状态机实例。
     * Spring 的自动装配机制会在定义此 Bean 时，自动从容器中找到 StateMachineFactory 实例并传递给该方法。
     * StateMachineService 提供了对状态机的管理功能，如启动、停止、发送事件等操作。
     *
     * @param stateMachineFactory 状态机工厂，用于创建具体的状态机实例
     * @return 配置好的 StateMachineService 实例
     */
    @Bean
    public StateMachineService<FSMConst.HealthCodeColor, FSMConst.HealthCodeEvent> stateMachineService(
            StateMachineFactory<HealthCodeColor, HealthCodeEvent> stateMachineFactory) {
        // 使用 DefaultStateMachineService 实现类创建 StateMachineService 实例，并传入状态机工厂
        return new DefaultStateMachineService<>(stateMachineFactory);
    }

    /**
     * 配置状态机的状态。
     * 该方法会被 Spring State Machine 框架调用，用于定义状态机的初始状态和所有可能的状态。
     *
     * @param states 状态机状态配置器，用于配置状态机的状态信息
     * @throws Exception 配置过程中可能出现的异常
     */
    @Override
    public void configure(StateMachineStateConfigurer<HealthCodeColor, HealthCodeEvent> states) throws Exception {
        states
                // 开始配置状态
                .withStates()
                // 设置状态机的初始状态为绿色健康码
                .initial(HealthCodeColor.GREEN)
                // 指定状态机包含 HealthCodeColor 枚举中定义的所有状态
                .states(EnumSet.allOf(HealthCodeColor.class));
    }

    /**
     * 配置状态机的状态转换规则。
     * 该方法会被 Spring State Machine 框架调用，用于定义状态机在接收到特定事件时如何从一个状态转换到另一个状态。
     *
     * @param transitions 状态机转换配置器，用于配置状态机的状态转换规则
     * @throws Exception 配置过程中可能出现的异常
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<HealthCodeColor, HealthCodeEvent> transitions) throws Exception {
        transitions
                // 开始配置外部状态转换（即从一个状态到另一个不同状态的转换）
                .withExternal()
                // 定义从绿色健康码状态转换到红色健康码状态的规则，触发事件为强制变红事件
                .source(HealthCodeColor.GREEN).target(HealthCodeColor.RED).event(HealthCodeEvent.FORCE_RED)
                // 继续配置其他状态转换规则
                .and()
                .withExternal().source(HealthCodeColor.GREEN).target(HealthCodeColor.YELLOW).event(HealthCodeEvent.FORCE_YELLOW)
                .and()
                .withExternal().source(HealthCodeColor.YELLOW).target(HealthCodeColor.RED).event(HealthCodeEvent.FORCE_RED)
                .and()
                .withExternal().source(HealthCodeColor.YELLOW).target(HealthCodeColor.GREEN).event(HealthCodeEvent.FORCE_GREEN)
                .and()
                .withExternal().source(HealthCodeColor.RED).target(HealthCodeColor.GREEN).event(HealthCodeEvent.FORCE_GREEN)
                .and()
                .withExternal().source(HealthCodeColor.RED).target(HealthCodeColor.YELLOW).event(HealthCodeEvent.FORCE_YELLOW);
    }
}