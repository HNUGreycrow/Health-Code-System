package org.software.code.common.consts;

/**
 * FSMConst 类用于定义与有限状态机（Finite State Machine, FSM）相关的常量。
 * 有限状态机常用于描述系统在不同状态之间的转换，这里主要涉及健康码状态及触发状态转换的事件。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public class FSMConst {

    /**
     * HealthCodeColor 枚举定义了健康码的三种颜色状态。
     * 健康码颜色通常代表着不同的健康风险等级，每种颜色对应特定的状态。
     */
    public enum HealthCodeColor {
        // 绿色健康码，表示低风险状态，通常意味着持有者健康状况良好
        GREEN,
        // 黄色健康码，表示中风险状态，可能持有者近期接触过风险人员或处于风险区域
        YELLOW,
        // 红色健康码，表示高风险状态，持有者可能是确诊病例、疑似病例或密切接触者等
        RED
    }

    /**
     * HealthCodeEvent 枚举定义了可以触发健康码颜色状态转换的事件。
     * 这些事件代表了一些特定的操作或条件，当发生这些事件时，健康码的颜色状态可能会改变。
     */
    public enum HealthCodeEvent {
        // 强制将健康码颜色设置为绿色的事件
        FORCE_GREEN,
        // 强制将健康码颜色设置为黄色的事件
        FORCE_YELLOW,
        // 强制将健康码颜色设置为红色的事件
        FORCE_RED
    }

}