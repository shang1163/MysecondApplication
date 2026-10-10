package cn.edu.sicnu.myapplication.model

/**
 * MVC 中的 Model 层：程序顾问。
 *
 * 只负责数据的存储与查询，不持有任何 View / Context 引用，
 * Controller 通过本类提供的方法获取结果后再刷新界面。
 */
class ProgramAdviser {

    /** 已添加的程序数据 */
    private val programs = mutableListOf<String>()

    /** 添加一条程序数据 */
    fun addProgram(program: String) {
        programs.add(program)
    }

    /**
     * 根据关键字查询程序。
     *
     * @param keyword 查询关键字
     * @return 第一条包含关键字的数据；关键字为空或无匹配时返回 null
     */
    fun queryProgram(keyword: String): String? {
        if (keyword.isEmpty()) return null
        return programs.firstOrNull { it.contains(keyword) }
    }

    /**
     * 根据语言特点推荐编程语言（对应教学视频中的 getLanguage）。
     *
     * @param featureIndex 特点在选项数组中的下标：
     *        0-快速 1-容易 2-新语言 3-面向对象
     * @return 推荐的编程语言；下标无匹配时返回 [ProgramLanguage.UNKNOWN]
     */
    fun recommendLanguage(featureIndex: Int): ProgramLanguage = when (featureIndex) {
        0 -> ProgramLanguage.C        // 快速
        1 -> ProgramLanguage.PYTHON   // 容易
        2 -> ProgramLanguage.KOTLIN   // 新语言
        3 -> ProgramLanguage.JAVA     // 面向对象
        else -> ProgramLanguage.UNKNOWN
    }
}

/** 可推荐的编程语言，与字符串资源中的语言名一一对应 */
enum class ProgramLanguage {
    C, PYTHON, KOTLIN, JAVA, UNKNOWN
}
