package com.opi.zoo

import com.opi.zoo.rest.domain.Animal
import com.opi.zoo.rest.domain.Keeper
import com.opi.zoo.rest.domain.AnimalType
import com.opi.zoo.rest.repository.AnimalRepository
import com.opi.zoo.rest.repository.KeeperRepository
import org.jfairy.Fairy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import javax.transaction.Transactional

@SpringBootApplication
class ZooApplication implements CommandLineRunner {

	@Autowired
	KeeperRepository keeperRepository

	@Autowired
	AnimalRepository animalRepository

	static void main(String[] args) {
		SpringApplication.run ZooApplication, args
	}

	@Override
	@Transactional
	void run(String... strings) throws Exception {
		Random random = new Random()
		Long currId = 0

		// setup the zoo keepers
		def keepers = keeperRepository.save([
				new Keeper(id: currId++, name: Fairy.create().person().fullName(), animals: []),
				new Keeper(id: currId++, name: Fairy.create().person().fullName(), animals: []),
				new Keeper(id: currId++, name: Fairy.create().person().fullName(), animals: []),
				new Keeper(id: currId++, name: Fairy.create().person().fullName(), animals: []),
				new Keeper(id: currId++, name: Fairy.create().person().fullName(), animals: [])
		])

		// assign animals to each keeper
		keepers.each {
			int animalCount = random.nextInt(15)
			for (int i = 0; i < animalCount; i++) {
				Fairy fairy = Fairy.create()
				it.animals << animalRepository.save(new Animal(
					id: currId++,
					name: fairy.person().firstName(),
					type: AnimalType.randomType(),
					birthdate: fairy.dateProducer().randomDateBetweenYearAndNow(1900).toDate().toInstant()
				))
			}
			keeperRepository.save(it)
		}
	}
}
