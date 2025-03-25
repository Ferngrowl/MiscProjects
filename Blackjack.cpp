#include <iostream>
#include <vector>
#include <ctime>
#include <cstdlib>
#include <string>
#include <algorithm>
#include <random>

using namespace std;

// Card structure to represent a playing card
struct Card {
    int value;      // Numerical value (1-13)
    char suit;      // Suit: clubs (c), diamonds (d), hearts (h), spades (s)
    
    // Default constructor
    Card() : value(0), suit('0') {}
    
    // Parameterized constructor
    Card(int val, char s) : value(val), suit(s) {}
    
    // Return a formatted string representation of the card
    string toString() const {
        string valueName;
        
        // Convert numerical value to proper card name
        switch (value) {
            case 1:  valueName = "Ace"; break;
            case 11: valueName = "Jack"; break;
            case 12: valueName = "Queen"; break;
            case 13: valueName = "King"; break;
            default: valueName = to_string(value); break;
        }
        
        // Convert suit character to full name
        string suitName;
        switch (suit) {
            case 'c': suitName = "Clubs"; break;
            case 'd': suitName = "Diamonds"; break;
            case 'h': suitName = "Hearts"; break;
            case 's': suitName = "Spades"; break;
            default:  suitName = "Unknown"; break;
        }
        
        return valueName + " of " + suitName;
    }
    
    // Get the blackjack value of the card
    int getBlackjackValue() const {
        if (value == 1) return 11;      // Ace is worth 11 by default (logic for Ace=1 is in hand calculation)
        else if (value > 10) return 10; // Face cards are worth 10
        else return value;              // Number cards are worth their face value
    }
};

// Class to manage the card deck
class Deck {
private:
    vector<Card> cards;
    
public:
    // Initialize and shuffle a fresh deck
    void setup() {
        cards.clear();
        static const char suits[] = {'c', 'd', 'h', 's'};
        
        // Create a standard 52-card deck
        for (int s = 0; s < 4; s++) {
            for (int val = 1; val <= 13; val++) {
                cards.push_back(Card(val, suits[s]));
            }
        }
        
        // Shuffle the deck
        shuffle();
    }
    
    // Shuffle the deck using modern C++ random engine
    void shuffle() {
        random_device rd;
        mt19937 g(rd());
        std::shuffle(cards.begin(), cards.end(), g); // Use std::shuffle with a generator
    }
    
    // Draw a card from the deck
    Card draw() {
        if (cards.empty()) {
            cout << "Warning: Deck is empty! Reshuffling..." << endl;
            setup();
        }
        
        Card drawnCard = cards.back();
        cards.pop_back();
        return drawnCard;
    }
    
    // Get number of cards remaining in the deck
    int cardsLeft() const {
        return cards.size();
    }
};

// Class to manage a player's hand of cards
class Hand {
private:
    vector<Card> cards;
    string name;
    
public:
    // Constructor with player name
    Hand(const string& playerName) : name(playerName) {}
    
    // Reset hand for a new game
    void clear() {
        cards.clear();
    }
    
    // Add a card to the hand
    void addCard(const Card& card) {
        cards.push_back(card);
    }
    
    // Calculate the value of the hand for Blackjack
    int getValue() const {
        int value = 0;
        int aces = 0;
        
        // First pass: calculate value treating Aces as 11
        for (const Card& card : cards) {
            int cardValue = card.getBlackjackValue();
            if (card.value == 1) aces++;
            value += cardValue;
        }
        
        // Second pass: adjust for Aces if total exceeds 21
        while (value > 21 && aces > 0) {
            value -= 10;  // Convert an Ace from 11 to 1
            aces--;
        }
        
        return value;
    }
    
    // Check if the hand is bust (over 21)
    bool isBust() const {
        return getValue() > 21;
    }
    
    // Check if the hand is a blackjack (21 with first two cards)
    bool isBlackjack() const {
        return cards.size() == 2 && getValue() == 21;
    }
    
    // Display the hand
    void display(bool hideFirstCard = false) const {
        cout << name << "'s hand: ";
        
        if (cards.empty()) {
            cout << "empty" << endl;
            return;
        }
        
        for (size_t i = 0; i < cards.size(); i++) {
            if (i == 0 && hideFirstCard) {
                cout << "[Hidden card] ";
            } else {
                cout << cards[i].toString() << " ";
            }
        }
        
        if (!hideFirstCard) {
            cout << "- Value: " << getValue();
        }
        cout << endl;
    }
    
    // Get player name
    string getName() const {
        return name;
    }
};

// Utility functions for input validation
char getYesNoInput(const string& prompt) {
    char response;
    bool validInput = false;
    
    do {
        cout << prompt << " (Y/N): ";
        cin >> response;
        response = toupper(response);
        
        if (response == 'Y' || response == 'N') {
            validInput = true;
        } else {
            cout << "Invalid input. Please enter Y or N." << endl;
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }
    } while (!validInput);
    
    return response;
}

// Main function to run the Blackjack game
int main() {
    cout << "====================================" << endl;
    cout << "   Welcome to Blackjack!" << endl;
    cout << "====================================" << endl;
    cout << "Rules: Try to get as close to 21 as possible without going over." << endl;
    cout << "       Dealer must hit on 16 and stand on 17." << endl;
    cout << "       Aces can count as 1 or 11 points." << endl;
    cout << "====================================" << endl;
    
    Deck deck;
    Hand playerHand("Player");
    Hand dealerHand("Dealer");
    
    bool gameOver = false;
    
    // Main game loop
    while (!gameOver) {
        // Setup for a new round
        deck.setup();
        playerHand.clear();
        dealerHand.clear();
        
        // Initial deal: 2 cards each
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        
        // Show initial hands (dealer's first card is hidden)
        cout << "\n--- New Round ---" << endl;
        dealerHand.display(true);
        playerHand.display();
        
        // Check for immediate blackjack
        if (playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            cout << "Both player and dealer have Blackjack! It's a push (tie)." << endl;
        } else if (playerHand.isBlackjack()) {
            cout << "Blackjack! You win 3:2 on your bet!" << endl;
        } else if (dealerHand.isBlackjack()) {
            dealerHand.display();
            cout << "Dealer has Blackjack! You lose." << endl;
        } else {
            // Player's turn
            bool playerTurnOver = false;
            
            while (!playerTurnOver) {
                char choice = getYesNoInput("Do you want to hit?");
                
                if (choice == 'Y') {
                    // Player takes a card
                    Card newCard = deck.draw();
                    cout << "You draw: " << newCard.toString() << endl;
                    playerHand.addCard(newCard);
                    playerHand.display();
                    
                    // Check if player busts
                    if (playerHand.isBust()) {
                        cout << "Bust! You went over 21. You lose." << endl;
                        playerTurnOver = true;
                    } else if (playerHand.getValue() == 21) {
                        cout << "You hit 21! Standing automatically." << endl;
                        playerTurnOver = true;
                    }
                } else {
                    // Player stands
                    cout << "You stand with " << playerHand.getValue() << endl;
                    playerTurnOver = true;
                }
            }
            
            // Dealer's turn (only if player didn't bust)
            if (!playerHand.isBust()) {
                cout << "\n--- Dealer's turn ---" << endl;
                dealerHand.display();
                
                // Dealer hits until reaching at least 17
                while (dealerHand.getValue() < 17) {
                    Card newCard = deck.draw();
                    cout << "Dealer draws: " << newCard.toString() << endl;
                    dealerHand.addCard(newCard);
                    dealerHand.display();
                }
                
                // Determine the winner
                int playerValue = playerHand.getValue();
                int dealerValue = dealerHand.getValue();
                
                if (dealerHand.isBust()) {
                    cout << "Dealer busts! You win!" << endl;
                } else if (playerValue > dealerValue) {
                    cout << "You win with " << playerValue << " against dealer's " << dealerValue << "!" << endl;
                } else if (dealerValue > playerValue) {
                    cout << "Dealer wins with " << dealerValue << " against your " << playerValue << "." << endl;
                } else {
                    cout << "It's a push (tie) with " << playerValue << "." << endl;
                }
            }
        }
        
        // Ask if player wants to play another round
        char playAgain = getYesNoInput("Would you like to play again?");
        if (playAgain == 'N') {
            gameOver = true;
        }
    }
    
    cout << "\nThank you for playing Blackjack!" << endl;
    return 0;
}
