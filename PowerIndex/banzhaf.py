# ------- 
# ------- banzhaf - version 1.1
# -------
# ------- A simple self-contained function to compute the Banzhaf Power Index given:
# -------   * a list of integer weights for all voters in the system, sorted in ascending order
# -------   * the quota for the voting system
# -------
# ------- This is a slight modification (mainly vectorization) of the code by 
# ------- Ruben R. Puentedura (http://www.hippasus.com/resources/socialsoftware/index.html)
# ------- which was published under CC-License in 2004
# -------
# ------- This software is released under a Creative Commons 2.0 Attribution License:
# -------   You are free:
# -------     * to copy, distribute, display, and perform the work
# -------     * to make derivative works
# -------     * to make commercial use of the work
# -------   Under the following conditions:
# -------     * Attribution. You must give the original author credit.
# -------     * For any reuse or distribution, you must make clear to others the license terms of this work.
# -------     * Any of these conditions can be waived if you get permission from the copyright holder.
# ------- The full legal text for this license is available at:
# -------   http://creativecommons.org/licenses/by/2.0/legalcode
# -------
# ------- Background notes:
# -------   The algorithm used here is based upon the description in:
# -------     Leech, Dennis (2002), "Computation of Power Indices", Warwick Economic Research Papers, Number 644, University of Warwick.
# -------     Available online at:
# -------       http://www.warwick.ac.uk/~ecrac/twerp644.pdf
# -------   A good introduction to the Banzhaf Power Index can be found in:
# -------     Taylor, Alan D. (1995), "Mathematics and Politics: Strategy, Voting, Power and Proof", Springer-Verlag, New York, New York.
# -------
# ------- Program notes:
# -------   banzhaf(weight, quota) returns a list containing the Banzhaf Power Index for all voters in the system
# -------     * weight is a list of integer weights for all voters in the system, sorted in ascending order
# -------     * quota is an integer which designates the number of votes needed for a coalition to win
# -------   The program will run in a few seconds for "reasonable" scenarios:
# -------       * total number of voters on the order of up to a thousand or so;
# -------       * maximum number of votes per voter on the order of up to a hundred or so.
# -------   Even many "unreasonable" scenarios will run quickly.
# -------   For scenarios that take too long to run using this code, a Monte Carlo approach may work best - see Leech (2002) for more info.
# -------
# ------- Sample code notes:
# -------   To test banzhaf, try calling it with the following parameters:
# -------     test_weight = [1,2,2,4,4,4]
# -------     test_quota = 12
# -------   These parameters correspond to the EEC in 1958 - the members (ordered by increasing voting weight) were:
# -------     Luxembourg, Netherlands, Belgium, Italy, Germany, France
# -------   The result should be:
# -------     test_index = [0.0, 0.14285714285714285, 0.14285714285714285, 0.23809523809523808, 0.23809523809523808, 0.23809523809523808]
# -------

def banzhaf(weight, quota):

    max_order = sum(weight)

    polynomial = [1] + max_order*[0]               # create a list to hold the polynomial coefficients

    current_order = 0                              # compute the polynomial coefficients
    aux_polynomial = polynomial[:]
    for i in range(len(weight)):
        current_order = current_order + weight[i]
        offset_polynomial = weight[i]*[0]+polynomial
        for j in range(current_order+1):
            aux_polynomial[j] = polynomial[j] + offset_polynomial[j]
        polynomial = aux_polynomial[:]

    banzhaf_power = len(weight)*[0]                                 # create a list to hold the Banzhaf Power for each voter
    swings = quota*[0]                                              # create a list to compute the swings for each voter

    for i in range(len(weight)):                                    # compute the Banzhaf Power
        for j in range(quota):                                      # fill the swings list
            if (j<weight[i]):
                swings[j] = polynomial[j]
            else:
                swings[j] = polynomial[j] - swings[j-weight[i]]
        for k in range(weight[i]):                                  # fill the Banzhaf Power vector
            banzhaf_power[i] = banzhaf_power[i] + swings[quota-1-k]

    # Normalize Index
    total_power = float(sum(banzhaf_power))
    banzhaf_index = map(lambda x: x / total_power, banzhaf_power)

    return banzhaf_index

weight = [10,8,7,5,3]
quota = 17
print(list(banzhaf(weight, quota)))