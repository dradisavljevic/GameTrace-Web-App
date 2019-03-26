(function() {
	var app = angular.module('register', []);
	
	app.controller('RegistrationController', ['$scope', '$http', '$window', function($scope, $http, $window) {
		var control = this;
		control.user = {};
		control.userAvatar = null;
		control.userAvatarB = null;
		control.result = "";
		control.type=  [
		      {id: '1', name: 'Afghanistan'},
		      {id: '2', name: 'Albania'},
		      {id: '3', name: 'Algeria'},
		      {id: '4', name: 'Andorra'},
		      {id: '5', name: 'Angola'},
		      {id: '6', name: 'Antigua and Barbuda'},
		      {id: '7', name: 'Argentina'},
		      {id: '8', name: 'Armenia'},
		      {id: '9', name: 'Australia'},
		      {id: '10', name: 'Austria'},
		      {id: '11', name: 'Azerbaijan'},
		      {id: '12', name: 'Bahamas'},
		      {id: '13', name: 'Bahrain'},
		      {id: '14', name: 'Bangladesh'},
		      {id: '15', name: 'Barbados'},
		      {id: '16', name: 'Belarus'},
		      {id: '17', name: 'Belgium'},
		      {id: '18', name: 'Belize'},
		      {id: '19', name: 'Benin'},
		      {id: '20', name: 'Bhutan'},
		      {id: '21', name: 'Bolivia'},
		      {id: '22', name: 'Bosnia and Herzegovina'},
		      {id: '23', name: 'Botswana'},
		      {id: '24', name: 'Brazil'},
		      {id: '25', name: 'Brunei'},
		      {id: '26', name: 'Bulgaria'},
		      {id: '27', name: 'Burkina Faso'},
		      {id: '28', name: 'Burundi'},
		      {id: '29', name: 'Cambodia'},
		      {id: '30', name: 'Cameroon'},
		      {id: '31', name: 'Canada'},
		      {id: '32', name: 'Cape Verde'},
		      {id: '33', name: 'Central African Republic'},
		      {id: '34', name: 'Chad'},
		      {id: '35', name: 'Chile'},
		      {id: '36', name: 'China'},
		      {id: '37', name: 'Colombia'},
		      {id: '38', name: 'Comoros'},
		      {id: '39', name: 'Congo DR'},
		      {id: '40', name: 'Congo Republic'},
		      {id: '41', name: 'Cook Islands'},
		      {id: '42', name: 'Costa Rica'},
		      {id: '43', name: 'Croatia'},
		      {id: '44', name: 'Cuba'},
		      {id: '45', name: 'Cyprus'},
		      {id: '46', name: 'Czech Republic'},
		      {id: '47', name: 'Denmark'},
		      {id: '48', name: 'Djibouti'},
		      {id: '49', name: 'Dominica'},
		      {id: '50', name: 'Dominican Republic'},
		      {id: '51', name: 'East Timor'},
		      {id: '52', name: 'Ecuador'},
		      {id: '53', name: 'Egypt'},
		      {id: '54', name: 'El Salvador'},
		      {id: '55', name: 'Equatorial Guinea'},
		      {id: '56', name: 'Eritrea'},
		      {id: '57', name: 'Estonia'},
		      {id: '58', name: 'Ethiopia'},
		      {id: '59', name: 'Fiji'},
		      {id: '60', name: 'Finland'},
		      {id: '61', name: 'France'},
		      {id: '62', name: 'Gabon'},
		      {id: '63', name: 'Gambia'},
		      {id: '64', name: 'Georgia'},
		      {id: '65', name: 'Germany'},
		      {id: '66', name: 'Ghana'},
		      {id: '67', name: 'Greece'},
		      {id: '68', name: 'Grenada'},
		      {id: '69', name: 'Guatemala'},
		      {id: '70', name: 'Guinea'},
		      {id: '71', name: 'Guinea-Bissau'},
		      {id: '72', name: 'Guyana'},
		      {id: '73', name: 'Haiti'},
		      {id: '74', name: 'Honduras'},
		      {id: '75', name: 'Hungary'},
		      {id: '76', name: 'Iceland'},
		      {id: '77', name: 'India'},
		      {id: '78', name: 'Indonesia'},
		      {id: '79', name: 'Iran'},
		      {id: '80', name: 'Iraq'},
		      {id: '81', name: 'Ireland'},
		      {id: '82', name: 'Israel'},
		      {id: '83', name: 'Italy'},
		      {id: '84', name: 'Ivory Coast'},
		      {id: '85', name: 'Jamaica'},
		      {id: '86', name: 'Japan'},
		      {id: '87', name: 'Jordan'},
		      {id: '88', name: 'Kazakhstan'},
		      {id: '89', name: 'Kenya'},
		      {id: '90', name: 'Kiribati'},
		      {id: '91', name: 'Korea DPR'},
		      {id: '92', name: 'Kuwait'},
		      {id: '93', name: 'Kyrgyzstan'},
		      {id: '94', name: 'Laos'},
		      {id: '95', name: 'Latvia'},
		      {id: '96', name: 'Lebanon'},
		      {id: '97', name: 'Lesotho'},
		      {id: '98', name: 'Liberia'},
		      {id: '99', name: 'Libya'},
		      {id: '100', name: 'Liechtenstein'},
		      {id: '101', name: 'Lithuania'},
		      {id: '102', name: 'Luxembourg'},
		      {id: '103', name: 'Macedonia'},
		      {id: '104', name: 'Madagascar'},
		      {id: '105', name: 'Malawi'},
		      {id: '106', name: 'Malaysia'},
		      {id: '107', name: 'Maldives'},
		      {id: '108', name: 'Mali'},
		      {id: '109', name: 'Malta'},
		      {id: '110', name: 'Marshall Islands'},
		      {id: '111', name: 'Mauritania'},
		      {id: '112', name: 'Mauritius'},
		      {id: '113', name: 'Mexico'},
		      {id: '114', name: 'Micronesia'},
		      {id: '115', name: 'Moldova'},
		      {id: '116', name: 'Monaco'},
		      {id: '117', name: 'Mongolia'},
		      {id: '118', name: 'Montenegro'},
		      {id: '119', name: 'Morocco'},
		      {id: '120', name: 'Mozambique'},
		      {id: '121', name: 'Myanmar'},
		      {id: '122', name: 'Namibia'},
		      {id: '123', name: 'Nauru'},
		      {id: '124', name: 'Nepal'},
		      {id: '125', name: 'Netherlands'},
		      {id: '126', name: 'New Zealand'},
		      {id: '127', name: 'Nicaragua'},
		      {id: '128', name: 'Niger'},
		      {id: '129', name: 'Nigeria'},
		      {id: '130', name: 'Norway'},
		      {id: '131', name: 'Oman'},
		      {id: '132', name: 'Pakistan'},
		      {id: '133', name: 'Palau'},
		      {id: '134', name: 'Palestine'},
		      {id: '135', name: 'Panama'},
		      {id: '136', name: 'Papua New Guinea'},
		      {id: '137', name: 'Paraguay'},
		      {id: '138', name: 'Peru'},
		      {id: '139', name: 'Philippines'},
		      {id: '140', name: 'Poland'},
		      {id: '141', name: 'Portugal'},
		      {id: '142', name: 'Qatar'},
		      {id: '143', name: 'Republic of Korea'},
		      {id: '144', name: 'Romania'},
		      {id: '145', name: 'Russia'},
		      {id: '146', name: 'Rwanda'},
		      {id: '147', name: 'Saint Kitts and Nevis'},
		      {id: '148', name: 'Saint Lucia'},
		      {id: '149', name: 'Saint Vincent and the Grenadines'},
		      {id: '150', name: 'Samoa'},
		      {id: '151', name: 'San Marino'},
		      {id: '152', name: 'Saudi Arabia'},
		      {id: '153', name: 'Senegal'},
		      {id: '154', name: 'Serbia'},
		      {id: '155', name: 'Seychelles'},
		      {id: '156', name: 'Sierra Leone'},
		      {id: '157', name: 'Singapore'},
		      {id: '158', name: 'Slovakia'},
		      {id: '159', name: 'Slovenia'},
		      {id: '160', name: 'Solomon Islands'},
		      {id: '161', name: 'Somalia'},
		      {id: '162', name: 'South Africa'},
		      {id: '163', name: 'South Sudan'},
		      {id: '164', name: 'Spain'},
		      {id: '165', name: 'Sri Lanka'},
		      {id: '166', name: 'Sudan'},
		      {id: '167', name: 'Suriname'},
		      {id: '168', name: 'Swaziland'},
		      {id: '169', name: 'Sweden'},
		      {id: '170', name: 'Switzerland'},
		      {id: '171', name: 'Syria'},
		      {id: '172', name: 'São Tomé and Príncipe'},
		      {id: '173', name: 'Taiwan'},
		      {id: '174', name: 'Tajikistan'},
		      {id: '175', name: 'Tanzania'},
		      {id: '176', name: 'Thailand'},
		      {id: '177', name: 'Togo'},
		      {id: '178', name: 'Tonga'},
		      {id: '179', name: 'Trinidad and Tobago'},
		      {id: '180', name: 'Tunisia'},
		      {id: '181', name: 'Turkey'},
		      {id: '182', name: 'Turkmenistan'},
		      {id: '183', name: 'Tuvalu'},
		      {id: '184', name: 'Uganda'},
		      {id: '185', name: 'Ukraine'},
		      {id: '186', name: 'United Arab Emirates'},
		      {id: '187', name: 'United Kingdom'},
		      {id: '188', name: 'United States'},
		      {id: '189', name: 'Uruguay'},
		      {id: '190', name: 'Uzbekistan'},
		      {id: '191', name: 'Vanuatu'},
		      {id: '192', name: 'Vatican'},
		      {id: '193', name: 'Venezuela'},
		      {id: '194', name: 'Vietnam'},
		      {id: '195', name: 'Yemen'},
		      {id: '196', name: 'Zambia'},
		      {id: '197', name: 'Zimbabwe'}
		    ];
		
		control.month = [
		      {id: '1', name: 'January'},
		      {id: '2', name: 'February'},
		      {id: '3', name: 'March'},
		      {id: '4', name: 'April'},
		      {id: '5', name: 'May'},
		      {id: '6', name: 'June'},
		      {id: '7', name: 'July'},
		      {id: '8', name: 'August'},
		      {id: '9', name: 'September'},
		      {id: '10', name: 'October'},
		      {id: '11', name: 'November'},
		      {id: '12', name: 'December'}
		 ]
		
		control.day = [
		      {id: '1', name: '1'},
		      {id: '2', name: '2'},
		      {id: '3', name: '3'},
		      {id: '4', name: '4'},
		      {id: '5', name: '5'},
		      {id: '6', name: '6'},
		      {id: '7', name: '7'},
		      {id: '8', name: '8'},
		      {id: '9', name: '9'},
		      {id: '10', name: '10'},
		      {id: '11', name: '11'},
		      {id: '12', name: '12'},
		      {id: '13', name: '13'},
		      {id: '14', name: '14'},
		      {id: '15', name: '15'},
		      {id: '16', name: '16'},
		      {id: '17', name: '17'},
		      {id: '18', name: '18'},
		      {id: '19', name: '19'},
		      {id: '20', name: '20'},
		      {id: '21', name: '21'},
		      {id: '22', name: '22'},
		      {id: '23', name: '23'},
		      {id: '24', name: '24'},
		      {id: '25', name: '25'},
		      {id: '26', name: '26'},
		      {id: '27', name: '27'},
		      {id: '28', name: '28'},
		      {id: '29', name: '29'},
		      {id: '30', name: '30'},
		      {id: '31', name: '31'}
		 ]
		
		
		control.year = [
		      {id: '1', name: '2017'},
		      {id: '2', name: '2016'},
		      {id: '3', name: '2015'},
		      {id: '4', name: '2014'},
		      {id: '5', name: '2013'},
		      {id: '6', name: '2012'},
		      {id: '7', name: '2011'},
		      {id: '8', name: '2010'},
		      {id: '9', name: '2009'},
		      {id: '10', name: '2008'},
		      {id: '11', name: '2007'},
		      {id: '12', name: '2006'},
		      {id: '13', name: '2005'},
		      {id: '14', name: '2004'},
		      {id: '15', name: '2003'},
		      {id: '16', name: '2002'},
		      {id: '17', name: '2001'},
		      {id: '18', name: '2000'},
		      {id: '19', name: '1999'},
		      {id: '20', name: '1998'},
		      {id: '21', name: '1997'},
		      {id: '22', name: '1996'},
		      {id: '23', name: '1995'},
		      {id: '24', name: '1994'},
		      {id: '25', name: '1993'},
		      {id: '26', name: '1992'},
		      {id: '27', name: '1991'},
		      {id: '28', name: '1990'},
		      {id: '29', name: '1989'},
		      {id: '30', name: '1988'},
		      {id: '31', name: '1987'},
		      {id: '32', name: '1986'},
		      {id: '33', name: '1985'},
		      {id: '34', name: '1984'},
		      {id: '35', name: '1983'},
		      {id: '36', name: '1982'},
		      {id: '37', name: '1981'},
		      {id: '38', name: '1980'},
		      {id: '39', name: '1979'},
		      {id: '40', name: '1978'},
		      {id: '41', name: '1977'},
		      {id: '42', name: '1976'},
		      {id: '43', name: '1975'},
		      {id: '44', name: '1974'},
		      {id: '45', name: '1973'},
		      {id: '46', name: '1972'},
		      {id: '47', name: '1971'},
		      {id: '48', name: '1970'},
		      {id: '49', name: '1969'},
		      {id: '50', name: '1968'},
		      {id: '51', name: '1967'},
		      {id: '52', name: '1966'},
		      {id: '53', name: '1965'},
		      {id: '54', name: '1964'},
		      {id: '55', name: '1963'},
		      {id: '56', name: '1962'},
		      {id: '57', name: '1961'},
		      {id: '58', name: '1960'},
		      {id: '59', name: '1959'},
		      {id: '60', name: '1958'},
		      {id: '61', name: '1957'},
		      {id: '62', name: '1956'},
		      {id: '63', name: '1955'},
		      {id: '64', name: '1954'},
		      {id: '65', name: '1953'},
		      {id: '66', name: '1952'},
		      {id: '67', name: '1951'},
		      {id: '68', name: '1950'},
		      {id: '69', name: '1949'},
		      {id: '70', name: '1948'},
		      {id: '71', name: '1947'},
		      {id: '72', name: '1946'},
		      {id: '73', name: '1945'},
		      {id: '74', name: '1944'},
		      {id: '75', name: '1943'},
		      {id: '76', name: '1942'},
		      {id: '77', name: '1941'},
		      {id: '78', name: '1940'},
		      {id: '79', name: '1939'},
		      {id: '80', name: '1938'},
		      {id: '81', name: '1937'},
		      {id: '82', name: '1936'},
		      {id: '83', name: '1935'},
		      {id: '84', name: '1934'},
		      {id: '85', name: '1933'},
		      {id: '86', name: '1932'},
		      {id: '87', name: '1931'},
		      {id: '88', name: '1930'},
		      {id: '89', name: '1929'},
		      {id: '90', name: '1928'},
		      {id: '91', name: '1927'},
		      {id: '92', name: '1926'},
		      {id: '93', name: '1925'},
		      {id: '94', name: '1924'},
		      {id: '95', name: '1923'},
		      {id: '96', name: '1922'},
		      {id: '97', name: '1921'},
		      {id: '98', name: '1920'},
		      {id: '99', name: '1919'},
		      {id: '100', name: '1918'},
		      {id: '101', name: '1917'},
		      {id: '102', name: '1916'},
		      {id: '103', name: '1915'},
		      {id: '104', name: '1914'},
		      {id: '105', name: '1913'},
		      {id: '106', name: '1912'},
		      {id: '107', name: '1911'},
		      {id: '108', name: '1910'},
		      {id: '109', name: '1909'},
		      {id: '110', name: '1908'},
		      {id: '111', name: '1907'},
		      {id: '112', name: '1906'},
		      {id: '111', name: '1905'},
		      {id: '111', name: '1904'},
		      {id: '111', name: '1903'},
		      {id: '111', name: '1902'},
		      {id: '111', name: '1901'},
		      {id: '111', name: '1900'}
		 ]
		
		
		
		control.selected = {id: '1', name: 'Afghanistan'};
		control.selectedMonth = {id: '1', name: 'January'};
		control.selectedDay = {id: '1', name: '1'};
		control.selectedYear = {id: '1', name: '2017'};
		
		
		$scope.$watch(angular.bind(this, function (selectedMonth) {
			  return control.selectedMonth;
			}), function (newVal, oldVal) {
			if(control.selectedMonth.id == "2" && ((parseInt(control.selectedYear.name))%4)!=0 ){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'}
				 ]
			} else if(control.selectedMonth.id == "2" && ((parseInt(control.selectedYear.name))%4)==0 ){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'}
				 ]
			} else if(control.selectedMonth.id == "1" || control.selectedMonth.id == "3" || control.selectedMonth.id == "5" || control.selectedMonth.id == "7" || control.selectedMonth.id == "8" || control.selectedMonth.id == "10" || control.selectedMonth.id == "12"){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'},
				      {id: '30', name: '30'},
				      {id: '31', name: '31'}
				 ]
			}  else if(control.selectedMonth.id == "4" || control.selectedMonth.id == "6" || control.selectedMonth.id == "9" || control.selectedMonth.id == "11"){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'},
				      {id: '30', name: '30'}
				 ]
			} 
			});
		
		$scope.$watch(angular.bind(this, function (selectedYear) {
			  return control.selectedYear;
			}), function (newVal, oldVal) {
			if(control.selectedMonth.id == "2" && ((parseInt(control.selectedYear.name))%4)!=0 ){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'}
				 ]
			} else if(control.selectedMonth.id == "2" && ((parseInt(control.selectedYear.name))%4)==0 ){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'}
				 ]
			} else if(control.selectedMonth.id == "1" || control.selectedMonth.id == "3" || control.selectedMonth.id == "5" || control.selectedMonth.id == "7" || control.selectedMonth.id == "8" || control.selectedMonth.id == "10" || control.selectedMonth.id == "12"){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'},
				      {id: '30', name: '30'},
				      {id: '31', name: '31'}
				 ]
			}  else if(control.selectedMonth.id == "4" || control.selectedMonth.id == "6" || control.selectedMonth.id == "9" || control.selectedMonth.id == "11"){
				control.day = [
				      {id: '1', name: '1'},
				      {id: '2', name: '2'},
				      {id: '3', name: '3'},
				      {id: '4', name: '4'},
				      {id: '5', name: '5'},
				      {id: '6', name: '6'},
				      {id: '7', name: '7'},
				      {id: '8', name: '8'},
				      {id: '9', name: '9'},
				      {id: '10', name: '10'},
				      {id: '11', name: '11'},
				      {id: '12', name: '12'},
				      {id: '13', name: '13'},
				      {id: '14', name: '14'},
				      {id: '15', name: '15'},
				      {id: '16', name: '16'},
				      {id: '17', name: '17'},
				      {id: '18', name: '18'},
				      {id: '19', name: '19'},
				      {id: '20', name: '20'},
				      {id: '21', name: '21'},
				      {id: '22', name: '22'},
				      {id: '23', name: '23'},
				      {id: '24', name: '24'},
				      {id: '25', name: '25'},
				      {id: '26', name: '26'},
				      {id: '27', name: '27'},
				      {id: '28', name: '28'},
				      {id: '29', name: '29'},
				      {id: '30', name: '30'}
				 ]
			} 

		});
		
		
		
		function readURL(input) {
		    if (input.files && input.files[0]) {
		        var reader = new FileReader();

		        reader.onload = function (e) {
		            $('#avatar').attr('src', e.target.result);
		            control.userAvatarB = e.target.result;
		        }

		        reader.readAsDataURL(input.files[0]);
		    }
		}
		
		$("#file").change(function(){
			readURL(this);
		});
		
		 function dataURItoBlob(dataURI) {
		      var binary = atob(dataURI.split(',')[1]);
		      var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
		      var array = [];
		      for (var i = 0; i < binary.length; i++) {
		        array.push(binary.charCodeAt(i));
		      }
		      return new Blob([new Uint8Array(array)], {
		        type: mimeString
		      });
		    }

		 this.validateEmail = function(email) {
			  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			  return re.test(email);
			}
		
		this.removeImg = function() {
			control.userAvatarB = null;
			control.userAvatar = null;
			$('#avatar').attr('src', null);
		}
		
		this.register = function() {
			control.result = "";
				if (control.user.password == control.user.repeated) {
					
					if(control.validateEmail(control.user.email)){
						if (control.selectedMonth.id == "2" && control.selectedDay.id == "30" ){
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "2" && control.selectedDay.id == "31" ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "4" && control.selectedDay.id == "31" ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "6" && control.selectedDay.id == "31" ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "9" && control.selectedDay.id == "31" ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "11" && control.selectedDay.id == "31" ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else if (control.selectedMonth.id == "2" && control.selectedDay.id == "29" && ((parseInt(control.selectedYear.name))%4)!=0 ) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.result = "Invalid Date.";
							control.user.password = "";
							control.user.repeated = "";
						} else {
						var proba = "";
						proba = control.selectedYear.name;
						proba = proba + "-" + control.selectedMonth.id + "-" + control.selectedDay.name;
						var elem = document.getElementById("RegisterResult");
						elem.style.color = "Black";
						control.result = "Processing request..."
						var newUser = {}
						newUser.uemail = control.user.email;
						newUser.pword = control.user.password;
						newUser.uname = control.user.username;
						newUser.ucountry = control.selected.name;
						newUser.ubio = control.user.bio;
						newUser.udob =proba;
						newUser.uavat = control.userAvatarB;
						newUser.rname = control.user.name;
						
						$http.post('/register/token', newUser).then(function success(response) {
							var elem = document.getElementById("RegisterResult");

							elem.style.color = "Green";
							if($scope.result =='A confirmation link has been sent to provided e-mail address.'){
								elem.style.color = "Green";
							} else {
								control.user.password = "";
								control.user.repeated = "";
							}
							control.result = response.data;
							
						}, function error(response) {
							var elem = document.getElementById("RegisterResult");
							elem.style.color = "Red";
							control.user.password = "";
							control.user.repeated = "";
							control.result = 'Unkown error occured. Please re-check all your inputs, reload the page and try again later';
						});
						if($scope.result =='A confirmation link has been sent to provided e-mail address.'){
							elem.style.color = "Green";
						}
						}
					} else {
						var elem = document.getElementById("RegisterResult");
						elem.style.color = "Red";
						control.user.password = "";
						control.user.repeated = "";
						control.result = 'Please input a valid email address';
					}
				} else {
					var elem = document.getElementById("RegisterResult");
					elem.style.color = "Red";
					control.user.password = "";
					control.user.repeated = "";
					control.result = "Passwords must match.";
				}
			
		}
	}]);
})();