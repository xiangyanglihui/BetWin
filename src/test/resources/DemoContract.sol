contract DemoContract {
	    address issuer;
	    mapping (address => uint) balances;

	    event Transfer(address from, address to, uint amount);

	    function DemoContract() {
	        issuer = msg.sender;
	    }

	    function issue(address account, uint amount) {
	        if (msg.sender != issuer) throw;
	        balances[account] += amount;
	    }

	    function transfer(address to, uint amount) {
	        if (balances[msg.sender] < amount) throw;

	        balances[msg.sender] -= amount;
	        balances[to] += amount;

	        Transfer(msg.sender, to, amount);
	    }

	    function getBalance(address account) constant returns (uint) {
	        return balances[account];
	    }
}