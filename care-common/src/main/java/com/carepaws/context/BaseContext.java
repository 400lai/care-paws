package com.carepaws.context;

/**
 * 基础上下文工具类，用于在当前线程中存储和获取用户ID信息
 * 使用ThreadLocal实现线程隔离的ID存储
 */
public class BaseContext {

    /**
     * 线程本地变量，用于存储当前线程的ID信息
     */
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的ID
     * @param id 需要设置的用户ID，类型为Long
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取当前线程的ID
     * @return 返回当前线程中存储的用户ID，如果未设置则返回null
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 移除当前线程的ID
     * 用于清理线程本地变量，防止内存泄漏
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}