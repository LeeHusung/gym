package workout.gym;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import workout.gym.domain.community.answer.CommunityAnswerRepository;
import workout.gym.domain.entity.CommunityAnswer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class GymApplicationTests {

	@Autowired private CommunityAnswerRepository communityAnswerRepository;

	@Test
	void testJpa() {
		CommunityAnswer communityAnswer = new CommunityAnswer();
		communityAnswer.setContent("커뮤니티 댓글");
		communityAnswerRepository.save(communityAnswer);

		List<CommunityAnswer> all = communityAnswerRepository.findAll();
		assertThat(all.get(0).getContent()).isEqualTo("커뮤니티 댓글");
	}

	@Test
	void testJpa2() {
		CommunityAnswer communityAnswer = new CommunityAnswer();
		communityAnswer.setContent("커뮤니티 댓글");
		communityAnswerRepository.save(communityAnswer);

		Optional<CommunityAnswer> ca = communityAnswerRepository.findById(1L);
		if (ca.isPresent()) {
			assertThat(ca.get().getContent()).isEqualTo("커뮤니티 댓글");
		}
	}

	@Test
	void testJpa3() {
		CommunityAnswer communityAnswer = new CommunityAnswer();
		communityAnswer.setContent("커뮤니티 댓글");
		communityAnswerRepository.save(communityAnswer);

//		CommunityAnswer ca = communityAnswerRepository.findBySubject("커뮤니티 댓글");
//		assertThat(ca.getId()).isEqualTo(1L);
	}

}
