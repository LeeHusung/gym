package workout.gym.web.workoutinfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WorkoutInfoController {

    @GetMapping("/workoutInfo")
    public String goWorkoutInfo() {
        return "workoutInfo";
    }
}
