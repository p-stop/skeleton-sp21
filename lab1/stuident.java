import java.util.Scanner;

// 定义课程类
class Course {
    private String courseId;
    private int credits;
    private int startTime;
    private int endTime;
    private String teacher;
    private String assessmentMethod;

    public Course(String courseId, int credits, int startTime, int endTime, String teacher, String assessmentMethod) {
        this.courseId = courseId;
        this.credits = credits;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacher = teacher;
        this.assessmentMethod = assessmentMethod;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "课程号: " + courseId + ", 学分: " + credits + ", 上课时间: " + startTime + ", 下课时间: " + endTime +
                ", 教师: " + teacher + ", 考核方式: " + assessmentMethod;
    }
}

// 定义学生类
class Student {
    private String name;
    private String studentId;
    private static final int COURSE_COUNT = 3;
    private String[] selectedCourses;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.selectedCourses = new String[COURSE_COUNT];
    }

    public void selectCourse(int index, String courseId) {
        selectedCourses[index] = courseId;
    }

    public void printSelectedCourses() {
        System.out.print("选课结果: ");
        for (String courseId : selectedCourses) {
            System.out.print(courseId + " ");
        }
        System.out.println();
    }






    public static void main(String[] args) {
        // 创建课程对象
        Course[] courses = {
                new Course("001", 1, 7, 8, "Joyce", "assignment"),
                new Course("002", 1, 8, 9, "Joyce", "assignment"),
                new Course("003", 1, 12, 15, "Joyce", "assignment"),
                new Course("004", 1, 18, 19, "Joyce", "assignment")
        };

        // 创建学生对象
        Student a = new Student("你的姓名", "你的学号");

        // 显示可以选修的课程
        System.out.println("可以选修的课程:");
        for (Course course : courses) {
            System.out.println(course);
        }

        // 要求学生逐个输入选的课程号
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < Student.COURSE_COUNT; i++) {
            System.out.print("请输入第 " + (i + 1) + " 门课程号: ");
            String courseId = scanner.nextLine();
            a.selectCourse(i, courseId);
        }

        // 打印选课结果
        a.printSelectedCourses();

        scanner.close();
    }
}
