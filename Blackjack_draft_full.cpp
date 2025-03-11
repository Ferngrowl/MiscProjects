#include <iostream>
#include <ctime>
#include <vector>
#include <cstdlib>
#include <cmath>

using namespace std;


//funtions and structs

struct Card
{
	int value; //initialising variables
	char suit;

	Card() {} //default constructor
	Card(int value, char suit) : value(value), suit(suit) {} // a card is defined by taking value and suit from the deck setup function
};

vector<Card> deck;

void setupDeck()
{
	deck.clear(); //gives us a blank array to refresh our deck during setup
	static const char suit[] = { 'c','d','h','s' }; //defining our constant: suits

	for (int s = 0; s<4; s++) // a shorthand way of giving values appropriately to each suit, for each of our four suits, 13 cards are given a value before moving on to the next suit
		for (int val = 1; val <= 13; val++) //GIVES CARDS VALUES UP TO 13, FIX THIS
			deck.push_back(Card(val, suit[s]));
}

Card drawRandomCard()
{
	srand(time(NULL));
	int randIndex = rand() % deck.size();
	Card drawnCard = deck[randIndex];
	deck.erase(deck.begin() + randIndex);
	return drawnCard;

}


// main

void main() {
	// welcome message and initialising our bool for the games loop
	cout << "Welcome to blackjack!" << endl;
	bool IsgameOver = false;
	
	// loop for continous play 
	while (!IsgameOver) {
		setupDeck();
		char hit;
		bool IsPturnOver = false;
		int Phand = 0;
		int Dhand = 0;


		// players turn
		while (!IsPturnOver) {
			cout << "Do you want a card? (Y)es / (N)o" << endl;
			cin >> hit;

			if (hit == 'Y') {
				Card result = drawRandomCard();

				Phand += result.value;

				cout << "You received a " << result.value << " of " << result.suit << ", current total value at " << Phand << endl;

				if (Phand > 21) {
					cout << "Sorry, you are bust, YOU LOSE! " << "( " << Phand << " )" << endl;
					IsPturnOver = true;
				}
				if (Phand == 21) {
					cout << "You should probably stand now." << endl;

				}
			}
			else if (hit == 'N') {
				cout << "you are standing on: " << Phand << endl;
				IsPturnOver = true;
			}
			else {
				cout << "Must be a capital, and numbers are not allowed. Invalid choice, please try again" << endl;
			}
		}


		//dealers turn
		while (Dhand < 17 && Phand <= 21) {
			Card dealer = drawRandomCard();
			Dhand += dealer.value;
			cout << "dealer draws a " << dealer.value << " of " << dealer.suit << " dealer's hand sits at: " << Dhand << endl;
		}

		//Win/lose conditions

		if (Dhand > 21 && Phand <= 21) {
			cout << "dealer is bust, you win!" << endl;;
		}
		if (Dhand > 21 && Phand > 21) {
			cout << "Both you and the dealer went bust! Nobody wins!" << endl;
		}
		if (Dhand == Phand && Dhand <= 21 && Phand <= 21) {
			cout << "Both you and the dealer have the same hand! It's a tie!" << endl;
		}
		if (Phand > Dhand && Phand <= 21) {
			cout << "You have a higher score than the dealer! YOU WIN!" << endl;
		}
		if (Dhand > Phand && Dhand <= 21) {
			cout << "Dealer has a higher score than you! YOU LOSE!" << endl;
		}

		//play again
		char replay;
		do {
			cout << "would you like to play again? (Y/N)" << endl;
			cin >> replay;

			if (replay == 'N') {
				IsgameOver = true;
				break;
			} else if (replay != 'Y'){
				cout << "Must be a capital, and numbers are not allowed. Invalid choice, please try again" << endl;
			}
		} while (replay != 'Y');
	
	// end of while loop 'isgameover'	
	}

	// end of main, display goodbye message and close
	cout << "The game is over now, thanks for playing!" << endl;
	exit(EXIT_SUCCESS);
}

	
	


	