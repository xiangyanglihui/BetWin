contract DemoContract {
	    address issuer;
	    mapping (address => int) balances;

	    event Transfer(address from, address to, int amount);

	    function DemoContract() {
	        issuer = msg.sender;
	    }

	    function issue(address account, int amount) {
	        if (msg.sender != issuer) throw;
	        balances[account] += amount;
	    }

	    function transfer(address to, int amount) {
	        if (balances[msg.sender] < amount) throw;

	        balances[msg.sender] -= amount;
	        balances[to] += amount;

	        Transfer(msg.sender, to, amount);
	    }

	    function getBalance(address account) constant returns (uint) {
	        return balances[account];
	    }
	    
	    function withdraw(address to){
	      to.send(balances[to]);
	    }
}