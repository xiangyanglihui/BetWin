pragma solidity ^0.4.0;

contract Coin {
	    address issuer;
	    mapping (address => uint256) balances;

	    function Coin() {
	        issuer = msg.sender;
	    }

	    function issue(address account, uint256 amount) {
	        if (msg.sender != issuer) throw;
	        balances[account] += amount;
	    }

	    function transfer(address to, uint256 amount) {
	        if (balances[msg.sender] < amount) throw;

	        balances[msg.sender] -= amount;
	        balances[to] += amount;

	    }

	    function getBalance(address account) constant returns (uint256) {
	        return balances[account];
	    }
}