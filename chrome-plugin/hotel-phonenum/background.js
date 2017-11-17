function checkForValidUrl(tabId, changeInfo, tab) {
    chrome.pageAction.show(tabId);
}

chrome.tabs.onUpdated.addListener(checkForValidUrl);

chrome.runtime.onMessage.addListener(function(request, sender, sendRequest){
	console.log(request);
});
